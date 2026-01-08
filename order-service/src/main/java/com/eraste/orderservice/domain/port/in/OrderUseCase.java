package com.eraste.orderservice.domain.port.in;

import com.eraste.orderservice.domain.model.Order;
import com.eraste.orderservice.domain.model.OrderStatus;

import java.util.List;
import java.util.Optional;

/**
 * Input port defining the use cases for Order operations.
 * <p>
 * This interface follows the Hexagonal Architecture (Ports and Adapters) pattern.
 * It defines the primary/driving port that the application exposes to the outside world.
 * The implementation is provided by the application service layer.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
public interface OrderUseCase {

    /**
     * Creates a new order in the system.
     *
     * @param order the order to create (without ID)
     * @return the created order with generated ID, order number, and timestamps
     */
    Order createOrder(Order order);

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param id the unique identifier of the order
     * @return an Optional containing the order if found, empty otherwise
     */
    Optional<Order> getOrderById(Long id);

    /**
     * Retrieves an order by its order number.
     *
     * @param orderNumber the unique order number
     * @return an Optional containing the order if found, empty otherwise
     */
    Optional<Order> getOrderByOrderNumber(String orderNumber);

    /**
     * Retrieves all orders from the system.
     *
     * @return a list of all orders, empty list if no orders exist
     */
    List<Order> getAllOrders();

    /**
     * Retrieves all orders for a specific user.
     *
     * @param userId the user ID to filter by
     * @return a list of orders for the user
     */
    List<Order> getOrdersByUserId(Long userId);

    /**
     * Retrieves all orders with a specific status.
     *
     * @param status the status to filter by
     * @return a list of orders with the specified status
     */
    List<Order> getOrdersByStatus(OrderStatus status);

    /**
     * Updates the status of an existing order.
     *
     * @param id     the unique identifier of the order
     * @param status the new status
     * @return the updated order
     * @throws com.eraste.common.exception.ResourceNotFoundException if order not found
     * @throws IllegalStateException if status transition is not allowed
     */
    Order updateOrderStatus(Long id, OrderStatus status);

    /**
     * Cancels an order.
     *
     * @param id the unique identifier of the order to cancel
     * @return the cancelled order
     * @throws com.eraste.common.exception.ResourceNotFoundException if order not found
     * @throws IllegalStateException if order cannot be cancelled
     */
    Order cancelOrder(Long id);

    /**
     * Deletes an order from the system.
     *
     * @param id the unique identifier of the order to delete
     * @throws com.eraste.common.exception.ResourceNotFoundException if order not found
     */
    void deleteOrder(Long id);
}
