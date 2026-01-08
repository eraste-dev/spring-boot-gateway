package com.eraste.orderservice.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

/**
 * Data Transfer Object for order creation requests.
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@Schema(description = "Request object for creating an order")
public class OrderRequest {

    @Schema(description = "User ID placing the order", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "User ID is required")
    private Long userId;

    @Schema(description = "Shipping address", example = "123 Main St, City, Country 12345", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Shipping address is required")
    @Size(max = 500, message = "Shipping address must not exceed 500 characters")
    private String shippingAddress;

    @Schema(description = "Special instructions or notes", example = "Leave at door")
    @Size(max = 1000, message = "Notes must not exceed 1000 characters")
    private String notes;

    @Schema(description = "List of order items", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Order must have at least one item")
    @Valid
    private List<OrderItemRequest> items;

    public OrderRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}
