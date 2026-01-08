package com.eraste.productservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Domain entity representing a Product in the system.
 * <p>
 * This class is a pure domain model, independent of any infrastructure concerns
 * (database, framework, etc.). It contains the core business attributes and
 * behaviors related to a product.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
public class Product {

    /** Unique identifier of the product. */
    private Long id;

    /** Product name. */
    private String name;

    /** Product description. */
    private String description;

    /** Product price. */
    private BigDecimal price;

    /** Available quantity in stock. */
    private Integer quantity;

    /** Product SKU (Stock Keeping Unit). */
    private String sku;

    /** Product category. */
    private Category category;

    /** Whether the product is active/available. */
    private Boolean active;

    /** Timestamp when the product was created. */
    private LocalDateTime createdAt;

    /** Timestamp when the product was last updated. */
    private LocalDateTime updatedAt;

    /**
     * Default constructor required for frameworks.
     */
    public Product() {
        this.active = true;
    }

    /**
     * Constructs a Product with the specified attributes.
     *
     * @param id          the unique identifier
     * @param name        the product name
     * @param description the product description
     * @param price       the product price
     * @param quantity    the available quantity
     * @param sku         the SKU code
     * @param category    the product category
     */
    public Product(Long id, String name, String description, BigDecimal price,
                   Integer quantity, String sku, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.sku = sku;
        this.category = category;
        this.active = true;
    }

    /**
     * Checks if the product is in stock.
     *
     * @return true if quantity is greater than 0
     */
    public boolean isInStock() {
        return quantity != null && quantity > 0;
    }

    /**
     * Gets the unique identifier of the product.
     *
     * @return the product ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the product.
     *
     * @param id the product ID to set
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return the product category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the product category.
     *
     * @param category the category to set
     */
    public void setCategory(Category category) {
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

    /**
     * Gets the creation timestamp.
     *
     * @return the creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp.
     *
     * @param createdAt the creation timestamp to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the last update timestamp.
     *
     * @return the last update timestamp
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the last update timestamp.
     *
     * @param updatedAt the last update timestamp to set
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
