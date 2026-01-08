package com.eraste.productservice.domain.port.out;

import com.eraste.productservice.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Output port for Product persistence operations.
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
public interface ProductRepositoryPort {

    /**
     * Saves a product entity (create or update).
     *
     * @param product the product to save
     * @return the saved product with updated fields (ID, timestamps)
     */
    Product save(Product product);

    /**
     * Finds a product by its unique identifier.
     *
     * @param id the unique identifier
     * @return an Optional containing the product if found, empty otherwise
     */
    Optional<Product> findById(Long id);

    /**
     * Retrieves all products from the repository.
     *
     * @return a list of all products
     */
    List<Product> findAll();

    /**
     * Retrieves all active products.
     *
     * @return a list of active products
     */
    List<Product> findByActiveTrue();

    /**
     * Retrieves products by category ID.
     *
     * @param categoryId the category ID to filter by
     * @return a list of products in the category
     */
    List<Product> findByCategoryId(Long categoryId);

    /**
     * Deletes a product by its unique identifier.
     *
     * @param id the unique identifier of the product to delete
     */
    void deleteById(Long id);

    /**
     * Finds a product by its SKU.
     *
     * @param sku the SKU to search for
     * @return an Optional containing the product if found, empty otherwise
     */
    Optional<Product> findBySku(String sku);

    /**
     * Checks if a product exists with the given SKU.
     *
     * @param sku the SKU to check
     * @return true if a product with this SKU exists, false otherwise
     */
    boolean existsBySku(String sku);
}
