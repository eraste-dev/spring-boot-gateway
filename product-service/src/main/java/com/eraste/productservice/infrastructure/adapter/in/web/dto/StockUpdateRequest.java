package com.eraste.productservice.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for stock update requests.
 * <p>
 * This DTO is used for PATCH operations to update only the stock quantity
 * of a product.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@Schema(description = "Request object for updating product stock")
public class StockUpdateRequest {

    /** New quantity value. */
    @Schema(description = "New stock quantity", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    /**
     * Default constructor.
     */
    public StockUpdateRequest() {
    }

    /**
     * Constructs a StockUpdateRequest with the given quantity.
     *
     * @param quantity the new quantity
     */
    public StockUpdateRequest(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the quantity.
     *
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
