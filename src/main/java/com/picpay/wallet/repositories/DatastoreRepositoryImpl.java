package com.picpay.wallet.repositories;

import com.googlecode.objectify.Key;
import com.picpay.wallet.exceptions.BadRequestException;
import com.picpay.wallet.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Abstract class which implements a generic Datastore repository
 *
 * @param <K>
 * @param <T>
 */
public abstract class DatastoreRepositoryImpl<K, T> implements DatastoreRepository<K, T> {

    @Data
    @AllArgsConstructor
    public class QueryFilter {
        private String condition;
        private Object value;
    }

    @Data
    @AllArgsConstructor
    public class QueryStrict {
        private String[] columns;
        private String[] orderColumns;
        private Integer limit;
    }

    public enum QueryStrategy {
        AND, OR
    }

    private Class<K> keyClass;
    private Class<T> kindClass;

    public DatastoreRepositoryImpl(Class<K> keyClass, Class<T> entityClass) {
        this.keyClass = keyClass;
        this.kindClass = entityClass;
    }

    @Override
    public K save(T obj) {
        try {
            Key<T> key = ofy().save().entity(obj).now();
            return getKeyValue(key);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public Optional<T> findById(K id) {
        try {
            Key<T> key = createKey(id);
            T result = ofy().load().key(key).now();
            return Optional.ofNullable(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to load data", ex);
        }
    }

    @Override
    public Optional<Collection<T>> findByIds(List<K> ids) {
        try {
            Iterable<Key<T>> key = ids.stream().map(this::createKey).collect(Collectors.toList());
            Collection<T> result = ofy().load().keys(key).values();
            return Optional.of(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NotFoundException("Failed to load data");
        }
    }


    @Override
    public List<T> findAll() {
        List<T> entities;
        try {
            entities = ofy().load().type(kindClass).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NotFoundException("Failed to FindAll");
        }
        return entities;
    }

    @Override
    public List<T> findAllByUserKey(String userKey) {
        List<T> entities;
        try {

            entities = ofy().load().type(kindClass).filter("walletFromKey =", userKey).list();
            entities.addAll(ofy().load().type(kindClass).filter("walletToKey =", userKey).list());

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NotFoundException("Failed to FindAll");
        }
        return entities;
    }


    @Override
    public void delete(T obj) {
        try {
            if (obj != null) {
                ofy().delete().entity(obj).now();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenceException(ex);
        }
    }

    @Override
    public void deleteByKey(K key, T obj) {
        try {
            ofy().delete().keys(this.createKey(key)).now();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenceException(ex);
        }
    }

    /**
     * Creates a {@link Key} object for an entity in Datastore
     *
     * @param id Key id.
     * @return {@link Key} object.
     */
    @Override
    public Key<T> createKey(K id) {
        Key<T> key = null;
        if (keyClass.equals(String.class)) {
            key = Key.create(kindClass, (String) id);
        } else if (keyClass.equals(Long.class)) {
            key = Key.create(kindClass, (Long) id);
        }
        return key;
    }

    /**
     * Get the value from a given key.nThe AppEngine's key are generally {@link Long} or {@link String} type. This method is null safe, which means in case the
     * key was null, this method will returns null
     *
     * @param key key which contains the value
     * @return value the value of the given key
     */
    @SuppressWarnings("unchecked")
    private K getKeyValue(Key<T> key) {
        K keyValue = null;
        if (key != null) {
            if (keyClass.equals(String.class)) {
                keyValue = (K) key.getName();
            } else if (keyClass.equals(Long.class)) {
                keyValue = (K) (Long) key.getId();
            }
        }
        return keyValue;
    }

}
