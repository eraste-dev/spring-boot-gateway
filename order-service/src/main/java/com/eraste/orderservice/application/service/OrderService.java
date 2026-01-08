package com.eraste.orderservice.application.service;

import com.eraste.common.exception.ResourceNotFoundException;
import com.eraste.orderservice.domain.model.Order;
import com.eraste.orderservice.domain.model.OrderStatus;
import com.eraste.orderservice.domain.port.in.OrderUseCase;
import com.eraste.orderservice.domain.port.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Application service implementing order use cases.
 * <p>
 * This service acts as the orchestrator between the domain layer and infrastructure.
 * It implements the {@link OrderUseCase} input port and uses the {@link OrderRepositoryPort}
 * output port for persistence operations.
 * </p>
 * <p>
 * All methods are transactional by default. Read operations use read-only transactions
 * for better performance.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see OrderUseCase
 * @see OrderRepositoryPort
 */
@Service
@Transactional
public class OrderService implements OrderUseCase {

    private final OrderRepositoryPort orderRepository;

    /**
     * Constructs an OrderService with the required repository port.
     *
     * @param orderRepository the repository port for order persistence operations
     */
    public OrderService(OrderRepositoryPort orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Generates a unique order number and sets initial status to PENDING.
     * </p>
     */
    @Override
    public Order createOrder(Order order) {
        order.setOrderNumber(generateOrderNumber());
        order.setStatus(OrderStatus.PENDING);
        order.calculateTotalAmount();
        return orderRepository.save(order);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Order> getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Validates status transitions before updating.
     * </p>
     *
     * @throws IllegalStateException if status transition is not allowed
     */
    @Override
    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        validateStatusTransition(order.getStatus(), status);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalStateException if order cannot be cancelled
     */
    @Override
    public Order cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        if (!order.canBeCancelled()) {
            throw new IllegalStateException(
                    "Order cannot be cancelled. Current status: " + order.getStatus());
        }

        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOrder(Long id) {
        if (orderRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Order", "id", id);
        }
        orderRepository.deleteById(id);
    }

    /**
     * Generates a unique order number.
     * <p>
     * Format: ORD-YYYYMMDD-XXXXX (e.g., ORD-20240115-A1B2C)
     * </p>
     *
     * @return the generated order number
     */
    private String generateOrderNumber() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uniquePart = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        return "ORD-" + datePart + "-" + uniquePart;
    }

    /**
     * Validates if a status transition is allowed.
     *
     * @param currentStatus the current order status
     * @param newStatus     the new status to transition to
     * @throws IllegalStateException if transition is not allowed
     */
    private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        // Cannot change status of terminal orders
        if (currentStatus == OrderStatus.DELIVERED ||
            currentStatus == OrderStatus.CANCELLED ||
            currentStatus == OrderStatus.REFUNDED) {
            throw new IllegalStateException(
                    "Cannot change status of " + currentStatus + " order");
        }

        // Define valid transitions
        boolean validTransition = switch (currentStatus) {
            case PENDING -> newStatus == OrderStatus.CONFIRMED || newStatus == OrderStatus.CANCELLED;
            case CONFIRMED -> newStatus == OrderStatus.PROCESSING || newStatus == OrderStatus.CANCELLED;
            case PROCESSING -> newStatus == OrderStatus.SHIPPED || newStatus == OrderStatus.CANCELLED;
            case SHIPPED -> newStatus == OrderStatus.DELIVERED;
            default -> false;
        };

        if (!validTransition) {
            throw new IllegalStateException(
                    "Invalid status transition from " + currentStatus + " to " + newStatus);
        }
    }
}
