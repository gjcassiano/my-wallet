package com.wallet.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Generic service for CRUD operations
 *
 * @param <T> The entity in which the service is working on.
 */
public interface GenericService<T> {

    /**
     * Dedicated to create a new entity.
     *
     * @param entity what's going to be inserted.
     * @return The entity with its id.
     */
    T create(T entity);

    /**
     * It updates an entity A validation is done by searching in the database if an id exists.
     *
     * @param entity it's what's going to be updated
     * @return the updated entity
     * @throws javax.persistence.EntityNotFoundException If the entity id is not found.
     */
    T update(T entity);

    /**
     * Search the entity by id.
     *
     * @param id - performs the search based
     * @return optional with the entity if found or Optional empty.
     * @throws javax.persistence.EntityNotFoundException If it doesn't exist.
     */
    Optional<T> findById(Long id);

    /**
     * Search the entity by ids
     *
     * @param ids - performs the search based}
     * @return optional with the entity if found or Optional empty.
     * @throws javax.persistence.EntityNotFoundException If it doesn't exist.
     */
    List<T> findAllById(Set<Long> ids);

    /**
     * Search all table records.
     *
     * @return an entity list or an empty list if there isn't no records.
     */
    List<T> findAll();

    /**
     * Search all using a sort
     *
     * @param sort kind of sort
     * @return an entity list ordered or an empty list if there isn't no records.
     */
    List<T> findAll(Sort sort);

    /**
     * <p>
     * Validate if already exists a entity by one field unique.
     * </p>
     *
     * <ul>
     * <li>{@code validateIfAlreadyExist(1L, "Galvin name", repository::findByName, "The name 'Galvin name' already exists")}</li>
     * <li>{@code validateIfAlreadyExist(1L, new BuildPlan(){{ setId(22L); }}, repository::findByBuildPlan, "The build plan already exists")}</li>
     * </ul>
     *
     * @param id id from entity
     * @param fieldUnique any entity field value that is unique
     * @param method any method in your repository that accepts the type specified in <F> as a parameter and returns an Optional <T>
     * @param msgError error message for exception
     * @param <F> a field type of the entity
     */
    <F> void validateIfAlreadyExist(Long id, F fieldUnique, Function<F, Optional<T>> method, String msgError);

    /**
     * <p>
     * Validate that an entity already exists by two unique fields.
     * </p>
     *
     * <br>
     * <p>
     * BuildPlan buildplanVariable = new BuildPlan(){{ setId(22L); }};
     * </p>
     * <br>
     *
     * <ul>
     * <li>{@code validateIfAlreadyExist(1L, "Galvin name", buildplanVariable, repository::findByNameAndBuildPlan, "Error message...")}</li>
     * </ul>
     *
     * @param id id from entity
     * @param field any field value
     * @param otherField other field value different
     * @param method any method in your repository that accepts the type specified in <F> and <O> as parameters and returns an Optional <T>
     * @param msgError error message for exception
     * @param <F> a field type of the entity
     * @param <O> other field type of the entity
     */
    <F, O> void validateIfAlreadyExist(Long id, F field, O otherField, BiFunction<F, O, Optional<T>> method, String msgError);

    /**
     * It deletes an entity by id.
     *
     * @param id what's going to be deleted.
     * @throws javax.persistence.EntityNotFoundException If the id doesn't exist in database.
     */
    void deleteById(Long id);

    /**
     * Delete an entity by checking if the entity exists
     *
     * @param id id of entity
     * @param msgError message if an entity does not exist
     */
    void deleteByIdValidated(Long id, String msgError);

    /**
     * It deletes an entity list by their ids
     *
     * @param ids list what's going to be deleted.
     * @throws javax.persistence.EntityNotFoundException If one or more list ids don't exist in database
     */
    void deleteAllById(List<Long> ids);

    /**
     * Create a query for delete all entities specified in entities
     *
     * @param entities all for delete
     */
    void deleteInBatch(Collection<T> entities);

    /**
     * Create a query for delete all entities specified in entities using size
     *
     * @param entities all for delete
     * @param size quantity used by instruction
     */
    void deleteInBatch(Collection<T> entities, int size);

    /**
     * Search all table records using pagination.
     *
     * @param page number of page
     * @param size current elements presents per page
     * @param direction ascending or descending {@link Direction}
     * @param sortProperty a property from the entity to sort the table
     * @return a Page
     */
    Page<T> findPaginated(int page, int size, Direction direction, String sortProperty);

    /**
     * Find an entity by id.
     *
     * @param id from entity
     * @param msgError error message for exception
     * @return the entity if found
     * @throws javax.persistence.EntityNotFoundException with msgError If it doesn't exist.
     */
    T findByIdValidated(Long id, String msgError);

    /**
     * Saves all given entities.
     *
     * @param entities must not be {@literal null}
     * @return the saved entities
     */
    List<T> saveAll(Iterable<T> entities);

    /**
     * Flushes all pending changes to the database.
     */
    void flush();

    /**
     * Flushes all pending changes to the database and after clear entity manager.
     */
    void flushAndClear();

    /**
     * Delete a entity
     *
     * @param entity generic type
     */
    void delete(T entity);
}
