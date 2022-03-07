package com.picpay.wallet.repositories;

import com.googlecode.objectify.Key;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Interface which represents a generic repository for Datastore access
 *
 * @param <K> key class
 * @param <T> kind class
 * @author luizfon
 */
public interface DatastoreRepository<K, T> {

    /**
     * Save a given entity to the database
     *
     * @param obj entity to be saved
     * @return the created key
     */
    K save(T obj);

    /**
     * Select a entity using its identifier, and returns null if the entity doesn't exist
     *
     * @param id the entity's identifier
     * @return an {@link Optional} of entity instance
     */
    Optional<T> findById(K id);

    /**
     * Select a entity using its identifier, and returns empty if the entity doesn't exist
     *
     * @param ids the entity's identifier
     * @return some {@link Optional} of entity instance
     */
    Optional<Collection<T>> findByIds(List<K> ids);

    List<T> findAll();

    List<T> findAllByUserKey(String userKey);
    /**
     * Delete an content of entity by Object
     *
     * @param Obj entity that will be deleted
     */
    void delete(T Obj);

    /**
     * Delete a entity by Key
     *
     * @param id the value of the key
     */
    void deleteByKey(K id, T obj);

    /**
     * Create a Key from a generic
     *
     * @param id the value of the key
     * @return a Key with type defined
     */
    Key<T> createKey(K id);
}
