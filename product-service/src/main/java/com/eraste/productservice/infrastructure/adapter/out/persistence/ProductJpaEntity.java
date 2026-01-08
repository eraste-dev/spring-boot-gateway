package com.eraste.productservice.infrastructure.adapter.out.persistence;

import com.eraste.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * JPA Entity for Product persistence.
 * <p>
 * This class represents the database schema for products. It extends {@link BaseEntity}
 * which provides common fields like id, createdAt, and updatedAt.
 * </p>
 * <p>
 * This is an infrastructure concern, completely separate from the domain model.
 * The {@link ProductMapper} handles conversion between this entity and the domain model.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see BaseEntity
 * @see ProductMapper
 */
@Entity
@Table(name = "products")
public class ProductJpaEntity extends BaseEntity {

    /** Product name (max 100 characters). */
    @Column(nullable = false, length = 100)
    private String name;

    /** Product description (max 500 characters). */
    @Column(length = 500)
    private String description;

    /** Product price with precision. */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /** Available quantity in stock. */
    @Column(nullable = false)
    private Integer quantity;

    /** Unique SKU code (max 50 characters). */
    @Column(nullable = false, unique = true, length = 50)
    private String sku;

    /** Product category. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryJpaEntity category;

    /** Whether the product is active. */
    @Column(nullable = false)
    private Boolean active = true;

    /**
     * Default constructor required by JPA.
     */
    public ProductJpaEntity() {
    }

    /**
     * Constructs a ProductJpaEntity with all required fields.
     *
     * @param name        the product name
     * @param description the product description
     * @param price       the product price
     * @param quantity    the available quantity
     * @param sku         the unique SKU code
     * @param category    the product category entity
     */
    public ProductJpaEntity(String name, String description, BigDecimal price,
                            Integer quantity, String sku, CategoryJpaEntity category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.sku = sku;
        this.category = category;
        this.active = true;
    }

    /**
     * Gets the product name.
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     *
     * @param name the product name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the product description.
     *
     * @return the product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the product description.
     *
     * @param description the product description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the product price.
     *
     * @return the product price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     *
     * @param price the product price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets the available quantity.
     *
     * @return the available quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the available quantity.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the SKU code.
     *
     * @return the SKU code
     */
    public String getSku() {
        return sku;
    }

    /**
     * Sets the SKU code.
     *
     * @param sku the SKU code to set
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * Gets the product category.
     *
     * @return the product category entity
     */
    public CategoryJpaEntity getCategory() {
        return category;
    }

    /**
     * Sets the product category.
     *
     * @param category the category entity to set
     */
    public void setCategory(CategoryJpaEntity category) {
        this.category = category;
    }

    /**
     * Gets the active status.
     *
     * @return true if the product is active
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
}
