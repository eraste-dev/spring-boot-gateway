package com.eraste.orderservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain model representing a customer order.
 * <p>
 * This is the aggregate root for the Order bounded context.
 * It contains order details, customer reference, shipping information,
 * and a collection of order items.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
public class Order {

    /** Unique identifier of the order. */
    private Long id;

    /** Unique order number for external reference. */
    private String orderNumber;

    /** Reference to the user ID from user-service. */
    private Long userId;

    /** Current status of the order. */
    private OrderStatus status;

    /** Total amount of the order. */
    private BigDecimal totalAmount;

    /** Shipping address for the order. */
    private String shippingAddress;

    /** Customer notes or special instructions. */
    private String notes;

    /** List of items in the order. */
    private List<OrderItem> items = new ArrayList<>();

    /** Timestamp when the order was created. */
    private LocalDateTime createdAt;

    /** Timestamp when the order was last updated. */
    private LocalDateTime updatedAt;

    /**
     * Default constructor.
     */
    public Order() {
        this.status = OrderStatus.PENDING;
        this.items = new ArrayList<>();
    }

    /**
     * Adds an item to the order and recalculates the total.
     *
     * @param item the order item to add
     */
    public void addItem(OrderItem item) {
        this.items.add(item);
        calculateTotalAmount();
    }

    /**
     * Removes an item from the order and recalculates the total.
     *
     * @param item the order item to remove
     */
    public void removeItem(OrderItem item) {
        this.items.remove(item);
        calculateTotalAmount();
    }

    /**
     * Calculates the total amount based on all order items.
     */
    public void calculateTotalAmount() {
        this.totalAmount = items.stream()
                .map(OrderItem::getTotalPrice)
                .filter(price -> price != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Checks if the order can be cancelled.
     *
     * @return true if the order can be cancelled
     */
    public boolean canBeCancelled() {
        return status == OrderStatus.PENDING || status == OrderStatus.CONFIRMED;
    }

    /**
     * Checks if the order is in a terminal state.
     *
     * @return true if the order is in a terminal state
     */
    public boolean isTerminal() {
        return status == OrderStatus.DELIVERED ||
               status == OrderStatus.CANCELLED ||
               status == OrderStatus.REFUNDED;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
        calculateTotalAmount();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
