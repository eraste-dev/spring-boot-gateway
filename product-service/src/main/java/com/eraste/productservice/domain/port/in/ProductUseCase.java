package com.eraste.productservice.domain.port.in;

import com.eraste.productservice.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Input port defining the use cases for Product operations.
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
public interface ProductUseCase {

    /**
     * Creates a new product in the system.
     *
     * @param product the product to create (without ID)
     * @return the created product with generated ID and timestamps
     * @throws IllegalArgumentException if SKU already exists
     */
    Product createProduct(Product product);

    /**
     * Retrieves a product by its unique identifier.
     *
     * @param id the unique identifier of the product
     * @return an Optional containing the product if found, empty otherwise
     */
    Optional<Product> getProductById(Long id);

    /**
     * Retrieves all products from the system.
     *
     * @return a list of all products, empty list if no products exist
     */
    List<Product> getAllProducts();

    /**
     * Retrieves all active products.
     *
     * @return a list of active products
     */
    List<Product> getActiveProducts();

    /**
     * Retrieves products by category ID.
     *
     * @param categoryId the category ID to filter by
     * @return a list of products in the specified category
     */
    List<Product> getProductsByCategoryId(Long categoryId);

    /**
     * Updates an existing product with new information.
     *
     * @param id      the unique identifier of the product to update
     * @param product the product data to update
     * @return the updated product
     * @throws com.eraste.common.exception.ResourceNotFoundException if product not found
     */
    Product updateProduct(Long id, Product product);

    /**
     * Deletes a product from the system.
     *
     * @param id the unique identifier of the product to delete
     * @throws com.eraste.common.exception.ResourceNotFoundException if product not found
     */
    void deleteProduct(Long id);

    /**
     * Retrieves a product by its SKU.
     *
     * @param sku the SKU to search for
     * @return an Optional containing the product if found, empty otherwise
     */
    Optional<Product> getProductBySku(String sku);

    /**
     * Updates the stock quantity of a product.
     *
     * @param id       the product ID
     * @param quantity the new quantity
     * @return the updated product
     * @throws com.eraste.common.exception.ResourceNotFoundException if product not found
     */
    Product updateStock(Long id, Integer quantity);
}
