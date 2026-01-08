package com.eraste.productservice.domain.port.in;

import com.eraste.productservice.domain.model.Category;

import java.util.List;
import java.util.Optional;

/**
 * Input port defining the use cases for Category operations.
 * <p>
 * This interface follows the Hexagonal Architecture (Ports and Adapters) pattern.
 * It defines the primary/driving port that the application exposes to the outside world.
 * The implementation is provided by the application service layer.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CategoryUseCase {

    /**
     * Creates a new category in the system.
     *
     * @param category the category to create (without ID)
     * @return the created category with generated ID and timestamps
     * @throws IllegalArgumentException if code already exists
     */
    Category createCategory(Category category);

    /**
     * Retrieves a category by its unique identifier.
     *
     * @param id the unique identifier of the category
     * @return an Optional containing the category if found, empty otherwise
     */
    Optional<Category> getCategoryById(Long id);

    /**
     * Retrieves all categories from the system.
     *
     * @return a list of all categories, empty list if no categories exist
     */
    List<Category> getAllCategories();

    /**
     * Retrieves all active categories.
     *
     * @return a list of active categories
     */
    List<Category> getActiveCategories();

    /**
     * Updates an existing category with new information.
     *
     * @param id       the unique identifier of the category to update
     * @param category the category data to update
     * @return the updated category
     * @throws com.eraste.common.exception.ResourceNotFoundException if category not found
     */
    Category updateCategory(Long id, Category category);

    /**
     * Deletes a category from the system.
     *
     * @param id the unique identifier of the category to delete
     * @throws com.eraste.common.exception.ResourceNotFoundException if category not found
     */
    void deleteCategory(Long id);

    /**
     * Retrieves a category by its code.
     *
     * @param code the code to search for
     * @return an Optional containing the category if found, empty otherwise
     */
    Optional<Category> getCategoryByCode(String code);
}
