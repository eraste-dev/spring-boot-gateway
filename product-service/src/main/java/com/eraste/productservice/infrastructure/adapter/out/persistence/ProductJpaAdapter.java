package com.eraste.productservice.infrastructure.adapter.out.persistence;

import com.eraste.productservice.domain.model.Product;
import com.eraste.productservice.domain.port.out.ProductRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JPA Adapter implementing the ProductRepositoryPort.
 * <p>
 * This class serves as the secondary adapter (driven adapter) in the hexagonal architecture.
 * It implements the {@link ProductRepositoryPort} output port using Spring Data JPA.
 * </p>
 * <p>
 * This adapter can be swapped for another implementation (MongoDB, Cassandra, etc.)
 * without affecting the domain layer, as long as it implements the same port interface.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see ProductRepositoryPort
 * @see ProductJpaRepository
 * @see ProductMapper
 */
@Component
public class ProductJpaAdapter implements ProductRepositoryPort {

    private final ProductJpaRepository jpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final ProductMapper mapper;

    /**
     * Constructs a ProductJpaAdapter with required dependencies.
     *
     * @param jpaRepository         the Spring Data JPA repository for products
     * @param categoryJpaRepository the Spring Data JPA repository for categories
     * @param mapper                the mapper for domain/entity conversion
     */
    public ProductJpaAdapter(ProductJpaRepository jpaRepository,
                             CategoryJpaRepository categoryJpaRepository,
                             ProductMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.categoryJpaRepository = categoryJpaRepository;
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Handles both create and update operations. For updates, it loads the existing
     * entity to preserve audit fields and then applies changes.
     * Also resolves and sets the category entity based on the product's category.
     * </p>
     */
    @Override
    public Product save(Product product) {
        ProductJpaEntity entity;
        if (product.getId() != null) {
            entity = jpaRepository.findById(product.getId())
                    .orElse(mapper.toJpaEntity(product));
            mapper.updateJpaEntity(entity, product);
        } else {
            entity = mapper.toJpaEntity(product);
        }

        // Set category entity if category is provided
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            CategoryJpaEntity categoryEntity = categoryJpaRepository.findById(product.getCategory().getId())
                    .orElse(null);
            entity.setCategory(categoryEntity);
        } else {
            entity.setCategory(null);
        }

        ProductJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Product> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> findByActiveTrue() {
        return jpaRepository.findByActiveTrue().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        return jpaRepository.findByCategoryId(categoryId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Product> findBySku(String sku) {
        return jpaRepository.findBySku(sku)
                .map(mapper::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsBySku(String sku) {
        return jpaRepository.existsBySku(sku);
    }
}
