package com.eraste.orderservice.domain.port.out;

import com.eraste.orderservice.domain.model.Order;
import com.eraste.orderservice.domain.model.OrderStatus;

import java.util.List;
import java.util.Optional;

/**
 * Output port for Order persistence operations.
 * <p>
 * This interface follows the Hexagonal Architecture (Ports and Adapters) pattern.
 * It defines the secondary/driven port for persistence operations.
 * Implementations are provided by infrastructure adapters (JPA, MongoDB, etc.),
 * allowing the domain to remain independent of persistence technology.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
public interface OrderRepositoryPort {

    /**
     * Saves an order entity (create or update).
     *
     * @param order the order to save
     * @return the saved order with updated fields (ID, timestamps)
     */
    Order save(Order order);

    /**
     * Finds an order by its unique identifier.
     *
     * @param id the unique identifier
     * @return an Optional containing the order if found, empty otherwise
     */
    Optional<Order> findById(Long id);

    /**
     * Finds an order by its order number.
     *
     * @param orderNumber the unique order number
     * @return an Optional containing the order if found, empty otherwise
     */
    Optional<Order> findByOrderNumber(String orderNumber);

    /**
     * Retrieves all orders from the repository.
     *
     * @return a list of all orders
     */
    List<Order> findAll();

    /**
     * Retrieves all orders for a specific user.
     *
     * @param userId the user ID to filter by
     * @return a list of orders for the user
     */
    List<Order> findByUserId(Long userId);

    /**
     * Retrieves all orders with a specific status.
     *
     * @param status the status to filter by
     * @return a list of orders with the specified status
     */
    List<Order> findByStatus(OrderStatus status);

    /**
     * Deletes an order by its unique identifier.
     *
     * @param id the unique identifier of the order to delete
     */
    void deleteById(Long id);

    /**
     * Checks if an order exists with the given order number.
     *
     * @param orderNumber the order number to check
     * @return true if an order with this number exists, false otherwise
     */
    boolean existsByOrderNumber(String orderNumber);
}
