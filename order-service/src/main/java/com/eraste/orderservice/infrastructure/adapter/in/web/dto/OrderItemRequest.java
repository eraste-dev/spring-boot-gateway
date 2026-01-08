package com.eraste.orderservice.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Data Transfer Object for order item in requests.
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@Schema(description = "Order item data for creating orders")
public class OrderItemRequest {

    @Schema(description = "Product ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Product ID is required")
    private Long productId;

    @Schema(description = "Product name", example = "iPhone 15 Pro", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name must not exceed 100 characters")
    private String productName;

    @Schema(description = "Product SKU", example = "IPHONE-15-PRO-256", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Product SKU is required")
    @Size(max = 50, message = "Product SKU must not exceed 50 characters")
    private String productSku;

    @Schema(description = "Quantity ordered", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @Schema(description = "Unit price", example = "999.99", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than 0")
    private BigDecimal unitPrice;

    public OrderItemRequest() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
