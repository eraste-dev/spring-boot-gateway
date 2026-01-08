package com.eraste.productservice.infrastructure.adapter.out.persistence;

import com.eraste.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA Entity for Category persistence.
 * <p>
 * This class represents the database schema for categories. It extends {@link BaseEntity}
 * which provides common fields like id, createdAt, and updatedAt.
 * </p>
 * <p>
 * This is an infrastructure concern, completely separate from the domain model.
 * The {@link CategoryMapper} handles conversion between this entity and the domain model.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see BaseEntity
 * @see CategoryMapper
 */
@Entity
@Table(name = "categories")
public class CategoryJpaEntity extends BaseEntity {

    /** Category name (max 100 characters). */
    @Column(nullable = false, length = 100)
    private String name;

    /** Category description (max 500 characters). */
    @Column(length = 500)
    private String description;

    /** Unique category code (max 50 characters). */
    @Column(nullable = false, unique = true, length = 50)
    private String code;

    /** Whether the category is active. */
    @Column(nullable = false)
    private Boolean active = true;

    /** Products in this category. */
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<ProductJpaEntity> products = new ArrayList<>();

    /**
     * Default constructor required by JPA.
     */
    public CategoryJpaEntity() {
    }

    /**
     * Constructs a CategoryJpaEntity with all required fields.
     *
     * @param name        the category name
     * @param description the category description
     * @param code        the unique category code
     */
    public CategoryJpaEntity(String name, String description, String code) {
        this.name = name;
        this.description = description;
        this.code = code;
        this.active = true;
    }

    /**
     * Gets the category name.
     *
     * @return the category name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the category name.
     *
     * @param name the category name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the category description.
     *
     * @return the category description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the category description.
     *
     * @param description the category description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the category code.
     *
     * @return the category code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the category code.
     *
     * @param code the category code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the active status.
     *
     * @return true if the category is active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Sets the active status.
     *
     * @param active the active status to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Gets the products in this category.
     *
     * @return the list of products
     */
    public List<ProductJpaEntity> getProducts() {
        return products;
    }

    /**
     * Sets the products in this category.
     *
     * @param products the list of products to set
     */
    public void setProducts(List<ProductJpaEntity> products) {
        this.products = products;
    }
}
