package com.eraste.orderservice.infrastructure.adapter.out.persistence;

import com.eraste.orderservice.domain.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Order entities.
 * <p>
 * This interface provides CRUD operations and custom queries for {@link OrderJpaEntity}.
 * Spring Data JPA automatically generates the implementation at runtime.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see JpaRepository
 * @see OrderJpaEntity
 */
@Repository
public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, Long> {

    /**
     * Finds an order by its order number.
     *
     * @param orderNumber the order number to search for
     * @return an Optional containing the order entity if found
     */
    Optional<OrderJpaEntity> findByOrderNumber(String orderNumber);

    /**
     * Finds all orders for a specific user.
     *
     * @param userId the user ID to filter by
     * @return a list of order entities for the user
     */
    List<OrderJpaEntity> findByUserId(Long userId);

    /**
     * Finds all orders with a specific status.
     *
     * @param status the status to filter by
     * @return a list of order entities with the specified status
     */
    List<OrderJpaEntity> findByStatus(OrderStatus status);

    /**
     * Finds all orders for a user with a specific status.
     *
     * @param userId the user ID to filter by
     * @param status the status to filter by
     * @return a list of order entities matching both criteria
     */
    List<OrderJpaEntity> findByUserIdAndStatus(Long userId, OrderStatus status);

    /**
     * Checks if an order exists with the given order number.
     *
     * @param orderNumber the order number to check
     * @return true if an order exists with this number
     */
    boolean existsByOrderNumber(String orderNumber);
}
