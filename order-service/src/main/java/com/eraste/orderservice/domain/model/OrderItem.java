package com.eraste.orderservice.domain.model;

import java.math.BigDecimal;

/**
 * Domain model representing an item within an order.
 * <p>
 * Each OrderItem represents a single product line in an order,
 * including the quantity, unit price, and calculated total price.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
public class OrderItem {

    /** Unique identifier of the order item. */
    private Long id;

    /** Reference to the product ID from product-service. */
    private Long productId;

    /** Product name at the time of order (denormalized for history). */
    private String productName;

    /** Product SKU at the time of order. */
    private String productSku;

    /** Quantity ordered. */
    private Integer quantity;

    /** Unit price at the time of order. */
    private BigDecimal unitPrice;

    /** Total price for this line item (quantity * unitPrice). */
    private BigDecimal totalPrice;

    /**
     * Default constructor.
     */
    public OrderItem() {
    }

    /**
     * Constructs an OrderItem with all fields.
     *
     * @param productId   the product ID
     * @param productName the product name
     * @param productSku  the product SKU
     * @param quantity    the quantity ordered
     * @param unitPrice   the unit price
     */
    public OrderItem(Long productId, String productName, String productSku,
                     Integer quantity, BigDecimal unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productSku = productSku;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    /**
     * Calculates and updates the total price based on quantity and unit price.
     */
    public void calculateTotalPrice() {
        if (unitPrice != null && quantity != null) {
            this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
    }

    // Getters and Setters

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
        calculateTotalPrice();
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        calculateTotalPrice();
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
