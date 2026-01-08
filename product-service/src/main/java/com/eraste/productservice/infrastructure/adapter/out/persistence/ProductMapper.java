package com.eraste.productservice.infrastructure.adapter.out.persistence;

import com.eraste.productservice.domain.model.Product;
import org.springframework.stereotype.Component;

/**
 * Mapper between domain Product and JPA ProductJpaEntity.
 * <p>
 * This class handles the conversion between the domain model ({@link Product})
 * and the persistence model ({@link ProductJpaEntity}), maintaining the separation
 * of concerns between domain and infrastructure layers.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see Product
 * @see ProductJpaEntity
 */
@Component
public class ProductMapper {

    private final CategoryMapper categoryMapper;

    /**
     * Constructs a ProductMapper with required dependencies.
     *
     * @param categoryMapper the category mapper for category conversion
     */
    public ProductMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    /**
     * Converts a JPA entity to a domain model.
     * <p>
     * Maps all fields including audit timestamps (createdAt, updatedAt).
     * </p>
     *
     * @param entity the JPA entity to convert
     * @return the domain Product model, or null if entity is null
     */
    public Product toDomain(ProductJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        Product product = new Product();
        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setDescription(entity.getDescription());
        product.setPrice(entity.getPrice());
        product.setQuantity(entity.getQuantity());
        product.setSku(entity.getSku());
        product.setCategory(categoryMapper.toDomain(entity.getCategory()));
        product.setActive(entity.getActive());
        product.setCreatedAt(entity.getCreatedAt());
        product.setUpdatedAt(entity.getUpdatedAt());
        return product;
    }

    /**
     * Converts a domain model to a JPA entity.
     * <p>
     * Note: Timestamps are managed by JPA/Hibernate, not copied from domain.
     * Category must be set separately using setCategoryEntity method.
     * </p>
     *
     * @param product the domain Product model to convert
     * @return the JPA entity, or null if product is null
     */
    public ProductJpaEntity toJpaEntity(Product product) {
        if (product == null) {
            return null;
        }
        ProductJpaEntity entity = new ProductJpaEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        entity.setQuantity(product.getQuantity());
        entity.setSku(product.getSku());
        entity.setActive(product.getActive());
        // Category is set separately via setCategoryEntity
        return entity;
    }

    /**
     * Updates an existing JPA entity with values from a domain model.
     * <p>
     * Used for update operations to preserve the existing entity's ID and timestamps.
     * Category must be updated separately.
     * </p>
     *
     * @param entity  the existing JPA entity to update
     * @param product the domain Product model with new values
     */
    public void updateJpaEntity(ProductJpaEntity entity, Product product) {
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        entity.setQuantity(product.getQuantity());
        entity.setSku(product.getSku());
        entity.setActive(product.getActive());
        // Category is updated separately
    }
}
