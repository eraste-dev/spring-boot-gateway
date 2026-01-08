package com.eraste.orderservice.infrastructure.adapter.in.web.dto;

import com.eraste.orderservice.domain.model.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for order API responses.
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@Schema(description = "Response object containing order information")
public class OrderResponse {

    @Schema(description = "Order ID", example = "1")
    private Long id;

    @Schema(description = "Unique order number", example = "ORD-20240115-A1B2C")
    private String orderNumber;

    @Schema(description = "User ID", example = "1")
    private Long userId;

    @Schema(description = "User information")
    private UserInfo user;

    @Schema(description = "Order status", example = "PENDING")
    private OrderStatus status;

    @Schema(description = "Total amount", example = "1999.98")
    private BigDecimal totalAmount;

    @Schema(description = "Shipping address", example = "123 Main St, City, Country 12345")
    private String shippingAddress;

    @Schema(description = "Special instructions", example = "Leave at door")
    private String notes;

    @Schema(description = "Order items")
    private List<OrderItemResponse> items;

    @Schema(description = "Number of items in the order", example = "2")
    private Integer itemCount;

    @Schema(description = "Creation timestamp", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp", example = "2024-01-15T10:30:00")
    private LocalDateTime updatedAt;

    public OrderResponse() {
    }

    public OrderResponse(Long id, String orderNumber, Long userId, OrderStatus status,
                         BigDecimal totalAmount, String shippingAddress, String notes,
                         List<OrderItemResponse> items, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.userId = userId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.notes = notes;
        this.items = items;
        this.itemCount = items != null ? items.size() : 0;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
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

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
        this.itemCount = items != null ? items.size() : 0;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
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
