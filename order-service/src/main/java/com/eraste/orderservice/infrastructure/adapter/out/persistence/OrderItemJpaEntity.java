package com.eraste.orderservice.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * JPA Entity for OrderItem persistence.
 * <p>
 * This class represents a line item within an order in the database.
 * It stores product information at the time of order for historical accuracy.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@Entity
@Table(name = "order_items")
public class OrderItemJpaEntity {

    /** Unique identifier of the order item. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Reference to the parent order. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderJpaEntity order;

    /** Reference to the product ID from product-service. */
    @Column(name = "product_id", nullable = false)
    private Long productId;

    /** Product name at the time of order. */
    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    /** Product SKU at the time of order. */
    @Column(name = "product_sku", nullable = false, length = 50)
    private String productSku;

    /** Quantity ordered. */
    @Column(nullable = false)
    private Integer quantity;

    /** Unit price at the time of order. */
    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    /** Total price for this line item. */
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    /**
     * Default constructor required by JPA.
     */
    public OrderItemJpaEntity() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderJpaEntity getOrder() {
        return order;
    }

    public void setOrder(OrderJpaEntity order) {
        this.order = order;
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
