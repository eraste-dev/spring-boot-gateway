package com.eraste.productservice.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for product API responses.
 * <p>
 * This DTO represents the product data returned by the REST API.
 * It includes all product attributes plus computed fields and audit timestamps.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@Schema(description = "Response object containing product information")
public class ProductResponse {

    /** Unique identifier of the product. */
    @Schema(description = "Unique identifier of the product", example = "1")
    private Long id;

    /** Product name. */
    @Schema(description = "Product name", example = "iPhone 15 Pro")
    private String name;

    /** Product description. */
    @Schema(description = "Product description", example = "Latest Apple smartphone with A17 Pro chip")
    private String description;

    /** Product price. */
    @Schema(description = "Product price", example = "999.99")
    private BigDecimal price;

    /** Available quantity. */
    @Schema(description = "Available quantity in stock", example = "50")
    private Integer quantity;

    /** SKU code. */
    @Schema(description = "Stock Keeping Unit", example = "IPHONE-15-PRO-256")
    private String sku;

    /** Product category ID. */
    @Schema(description = "Product category ID", example = "1")
    private Long categoryId;

    @Schema(description = "Category Entity", implementation = CategoryResponse.class)
    private CategoryResponse category;

    /** Active status. */
    @Schema(description = "Whether the product is active", example = "true")
    private Boolean active;

    /** Stock availability status. */
    @Schema(description = "Whether the product is in stock", example = "true")
    private Boolean inStock;

    /** Timestamp when the product was created. */
    @Schema(description = "Timestamp when product was created", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;

    /** Timestamp when the product was last updated. */
    @Schema(description = "Timestamp when product was last updated", example = "2024-01-15T10:30:00")
    private LocalDateTime updatedAt;

    /**
     * Default constructor required for JSON serialization.
     */
    public ProductResponse() {
    }

    /**
     * Constructs a ProductResponse with all fields.
     *
     * @param id           the unique identifier
     * @param name         the product name
     * @param description  the product description
     * @param price        the product price
     * @param quantity     the available quantity
     * @param sku          the SKU code
     * @param categoryId   the product category ID
     * @param category     the product category entity
     * @param active       the active status
     * @param createdAt    the creation timestamp
     * @param updatedAt    the last update timestamp
     */
    public ProductResponse(Long id, String name, String description, BigDecimal price,
                           Integer quantity, String sku, Long categoryId, CategoryResponse category, Boolean active,
                           LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.sku = sku;
        this.categoryId = categoryId;
        this.category = category;
        this.active = active;
        this.inStock = quantity != null && quantity > 0;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Gets the product ID.
     *
     * @return the product ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the product ID.
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
        this.inStock = quantity != null && quantity > 0;
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
     * Gets the product category ID.
     *
     * @return the product category ID
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the product category ID.
     *
     * @param categoryId the category ID to set
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Gets the product category entity.
     *
     * @return the product category
     */
    public CategoryResponse getCategory() {
        return category;
    }

    /**
     * Sets the product category entity.
     *
     * @param category the category to set
     */
    public void setCategory(CategoryResponse category) {
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
     * Gets the in-stock status.
     *
     * @return true if the product is in stock
     */
    public Boolean getInStock() {
        return inStock;
    }

    /**
     * Sets the in-stock status.
     *
     * @param inStock the in-stock status to set
     */
    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
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
