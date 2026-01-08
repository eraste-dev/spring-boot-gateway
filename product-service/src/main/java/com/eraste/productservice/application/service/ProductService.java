package com.eraste.productservice.application.service;

import com.eraste.common.exception.ResourceNotFoundException;
import com.eraste.productservice.domain.model.Product;
import com.eraste.productservice.domain.port.in.ProductUseCase;
import com.eraste.productservice.domain.port.out.ProductRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Application service implementing product use cases.
 * <p>
 * This service acts as the orchestrator between the domain layer and infrastructure.
 * It implements the {@link ProductUseCase} input port and uses the {@link ProductRepositoryPort}
 * output port for persistence operations.
 * </p>
 * <p>
 * All methods are transactional by default. Read operations use read-only transactions
 * for better performance.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see ProductUseCase
 * @see ProductRepositoryPort
 */
@Service
@Transactional
public class ProductService implements ProductUseCase {

    private final ProductRepositoryPort productRepository;

    /**
     * Constructs a ProductService with the required repository port.
     *
     * @param productRepository the repository port for product persistence operations
     */
    public ProductService(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Validates that SKU is unique before creating the product.
     * </p>
     *
     * @throws IllegalArgumentException if SKU already exists
     */
    @Override
    public Product createProduct(Product product) {
        if (productRepository.existsBySku(product.getSku())) {
            throw new IllegalArgumentException("SKU already exists: " + product.getSku());
        }
        return productRepository.save(product);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> getActiveProducts() {
        return productRepository.findByActiveTrue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Updates all product fields except ID and timestamps.
     * </p>
     *
     * @throws ResourceNotFoundException if no product exists with the given ID
     */
    @Override
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setSku(product.getSku());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setActive(product.getActive());

        return productRepository.save(existingProduct);
    }

    /**
     * {@inheritDoc}
     *
     * @throws ResourceNotFoundException if no product exists with the given ID
     */
    @Override
    public void deleteProduct(Long id) {
        if (productRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        productRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductBySku(String sku) {
        return productRepository.findBySku(sku);
    }

    /**
     * {@inheritDoc}
     *
     * @throws ResourceNotFoundException if no product exists with the given ID
     */
    @Override
    public Product updateStock(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        product.setQuantity(quantity);
        return productRepository.save(product);
    }
}
