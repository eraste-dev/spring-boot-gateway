package com.eraste.productservice.domain.port.out;

import com.eraste.productservice.domain.model.Category;

import java.util.List;
import java.util.Optional;

/**
 * Output port for Category persistence operations.
 * <p>
 * This interface follows the Hexagonal Architecture (Ports and Adapters) pattern.
 * It defines the secondary/driven port for persistence operations.
 * Implementations are provided by infrastructure adapters (JPA, MongoDB, etc.),
 * allowing the domain to remain independent of persistence technology.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CategoryRepositoryPort {

    /**
     * Saves a category entity (create or update).
     *
     * @param category the category to save
     * @return the saved category with updated fields (ID, timestamps)
     */
    Category save(Category category);

    /**
     * Finds a category by its unique identifier.
     *
     * @param id the unique identifier
     * @return an Optional containing the category if found, empty otherwise
     */
    Optional<Category> findById(Long id);

    /**
     * Retrieves all categories from the repository.
     *
     * @return a list of all categories
     */
    List<Category> findAll();

    /**
     * Retrieves all active categories.
     *
     * @return a list of active categories
     */
    List<Category> findByActiveTrue();

    /**
     * Deletes a category by its unique identifier.
     *
     * @param id the unique identifier of the category to delete
     */
    void deleteById(Long id);

    /**
     * Finds a category by its code.
     *
     * @param code the code to search for
     * @return an Optional containing the category if found, empty otherwise
     */
    Optional<Category> findByCode(String code);

    /**
     * Checks if a category exists with the given code.
     *
     * @param code the code to check
     * @return true if a category with this code exists, false otherwise
     */
    boolean existsByCode(String code);
}
