package com.eraste.orderservice.infrastructure.adapter.out.persistence;

import com.eraste.common.entity.BaseEntity;
import com.eraste.orderservice.domain.model.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA Entity for Order persistence.
 * <p>
 * This class represents the database schema for orders. It extends {@link BaseEntity}
 * which provides common fields like id, createdAt, and updatedAt.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see BaseEntity
 */
@Entity
@Table(name = "orders")
public class OrderJpaEntity extends BaseEntity {

    /** Unique order number for external reference. */
    @Column(name = "order_number", nullable = false, unique = true, length = 30)
    private String orderNumber;

    /** Reference to the user ID from user-service. */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /** Current status of the order. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

    /** Total amount of the order. */
    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    /** Shipping address for the order. */
    @Column(name = "shipping_address", length = 500)
    private String shippingAddress;

    /** Customer notes or special instructions. */
    @Column(length = 1000)
    private String notes;

    /** List of items in the order. */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemJpaEntity> items = new ArrayList<>();

    /**
     * Default constructor required by JPA.
     */
    public OrderJpaEntity() {
    }

    /**
     * Adds an item to the order.
     *
     * @param item the order item to add
     */
    public void addItem(OrderItemJpaEntity item) {
        items.add(item);
        item.setOrder(this);
    }

    /**
     * Removes an item from the order.
     *
     * @param item the order item to remove
     */
    public void removeItem(OrderItemJpaEntity item) {
        items.remove(item);
        item.setOrder(null);
    }

    // Getters and Setters

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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

    public List<OrderItemJpaEntity> getItems() {
        return items;
    }

    public void setItems(List<OrderItemJpaEntity> items) {
        this.items = items;
    }
}
