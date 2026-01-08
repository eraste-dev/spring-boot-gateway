package com.eraste.productservice.infrastructure.adapter.out.persistence;

import com.eraste.productservice.domain.model.Category;
import org.springframework.stereotype.Component;

/**
 * Mapper between domain Category and JPA CategoryJpaEntity.
 * <p>
 * This class handles the conversion between the domain model ({@link Category})
 * and the persistence model ({@link CategoryJpaEntity}), maintaining the separation
 * of concerns between domain and infrastructure layers.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see Category
 * @see CategoryJpaEntity
 */
@Component
public class CategoryMapper {

    /**
     * Converts a JPA entity to a domain model.
     * <p>
     * Maps all fields including audit timestamps (createdAt, updatedAt).
     * </p>
     *
     * @param entity the JPA entity to convert
     * @return the domain Category model, or null if entity is null
     */
    public Category toDomain(CategoryJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        Category category = new Category();
        category.setId(entity.getId());
        category.setName(entity.getName());
        category.setDescription(entity.getDescription());
        category.setCode(entity.getCode());
        category.setActive(entity.getActive());
        category.setCreatedAt(entity.getCreatedAt());
        category.setUpdatedAt(entity.getUpdatedAt());
        return category;
    }

    /**
     * Converts a domain model to a JPA entity.
     * <p>
     * Note: Timestamps are managed by JPA/Hibernate, not copied from domain.
     * </p>
     *
     * @param category the domain Category model to convert
     * @return the JPA entity, or null if category is null
     */
    public CategoryJpaEntity toJpaEntity(Category category) {
        if (category == null) {
            return null;
        }
        CategoryJpaEntity entity = new CategoryJpaEntity();
        entity.setId(category.getId());
        entity.setName(category.getName());
        entity.setDescription(category.getDescription());
        entity.setCode(category.getCode());
        entity.setActive(category.getActive());
        return entity;
    }

    /**
     * Updates an existing JPA entity with values from a domain model.
     * <p>
     * Used for update operations to preserve the existing entity's ID and timestamps.
     * </p>
     *
     * @param entity   the existing JPA entity to update
     * @param category the domain Category model with new values
     */
    public void updateJpaEntity(CategoryJpaEntity entity, Category category) {
        entity.setName(category.getName());
        entity.setDescription(category.getDescription());
        entity.setCode(category.getCode());
        entity.setActive(category.getActive());
    }
}
