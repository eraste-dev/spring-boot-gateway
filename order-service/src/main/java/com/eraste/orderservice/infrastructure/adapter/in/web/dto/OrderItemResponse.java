package com.eraste.orderservice.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * Data Transfer Object for order item in responses.
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@Schema(description = "Order item information in response")
public class OrderItemResponse {

    @Schema(description = "Order item ID", example = "1")
    private Long id;

    @Schema(description = "Product ID", example = "1")
    private Long productId;

    @Schema(description = "Product name", example = "iPhone 15 Pro")
    private String productName;

    @Schema(description = "Product SKU", example = "IPHONE-15-PRO-256")
    private String productSku;

    @Schema(description = "Quantity ordered", example = "2")
    private Integer quantity;

    @Schema(description = "Unit price", example = "999.99")
    private BigDecimal unitPrice;

    @Schema(description = "Total price for this item", example = "1999.98")
    private BigDecimal totalPrice;

    public OrderItemResponse() {
    }

    public OrderItemResponse(Long id, Long productId, String productName, String productSku,
                             Integer quantity, BigDecimal unitPrice, BigDecimal totalPrice) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.productSku = productSku;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
