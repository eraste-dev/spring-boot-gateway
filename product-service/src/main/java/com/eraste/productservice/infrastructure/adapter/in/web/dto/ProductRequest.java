package com.eraste.productservice.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Data Transfer Object for product creation and update requests.
 * <p>
 * This DTO is used as the request body for POST and PUT operations
 * on the Product REST API. It includes validation constraints to ensure
 * data integrity before processing.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@Schema(description = "Request object for creating or updating a product")
public class ProductRequest {

    /** Product name (3-100 characters). */
    @Schema(description = "Product name", example = "iPhone 15 Pro", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    /** Product description (max 500 characters). */
    @Schema(description = "Product description", example = "Latest Apple smartphone with A17 Pro chip")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    /** Product price (positive value). */
    @Schema(description = "Product price", example = "999.99", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    /** Available quantity (non-negative). */
    @Schema(description = "Available quantity in stock", example = "50", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    /** Unique SKU code (3-50 characters). */
    @Schema(description = "Stock Keeping Unit (unique identifier)", example = "IPHONE-15-PRO-256", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "SKU is required")
    @Size(min = 3, max = 50, message = "SKU must be between 3 and 50 characters")
    private String sku;

    /** Product category ID. */
    @Schema(description = "Product category ID", example = "1")
    private Long categoryId;

    /** Active status. */
    @Schema(description = "Whether the product is active", example = "true")
    private Boolean active = true;

    /**
     * Default constructor required for JSON deserialization.
     */
    public ProductRequest() {
    }

    /**
     * Constructs a ProductRequest with all fields.
     *
     * @param name        the product name
     * @param description the product description
     * @param price       the product price
     * @param quantity    the available quantity
     * @param sku         the SKU code
     * @param categoryId  the product category ID
     * @param active      the active status
     */
    public ProductRequest(String name, String description, BigDecimal price,
                          Integer quantity, String sku, Long categoryId, Boolean active) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.sku = sku;
        this.categoryId = categoryId;
        this.active = active;
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
