package com.eraste.orderservice.infrastructure.adapter.out.persistence;

import com.eraste.orderservice.domain.model.Order;
import com.eraste.orderservice.domain.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Mapper between domain Order and JPA OrderJpaEntity.
 * <p>
 * This class handles the conversion between the domain model ({@link Order})
 * and the persistence model ({@link OrderJpaEntity}), maintaining the separation
 * of concerns between domain and infrastructure layers.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see Order
 * @see OrderJpaEntity
 */
@Component
public class OrderMapper {

    /**
     * Converts a JPA entity to a domain model.
     *
     * @param entity the JPA entity to convert
     * @return the domain Order model, or null if entity is null
     */
    public Order toDomain(OrderJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        Order order = new Order();
        order.setId(entity.getId());
        order.setOrderNumber(entity.getOrderNumber());
        order.setUserId(entity.getUserId());
        order.setStatus(entity.getStatus());
        order.setTotalAmount(entity.getTotalAmount());
        order.setShippingAddress(entity.getShippingAddress());
        order.setNotes(entity.getNotes());
        order.setCreatedAt(entity.getCreatedAt());
        order.setUpdatedAt(entity.getUpdatedAt());

        if (entity.getItems() != null) {
            order.setItems(entity.getItems().stream()
                    .map(this::toOrderItemDomain)
                    .collect(Collectors.toList()));
        }

        return order;
    }

    /**
     * Converts a domain model to a JPA entity.
     *
     * @param order the domain Order model to convert
     * @return the JPA entity, or null if order is null
     */
    public OrderJpaEntity toJpaEntity(Order order) {
        if (order == null) {
            return null;
        }
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setId(order.getId());
        entity.setOrderNumber(order.getOrderNumber());
        entity.setUserId(order.getUserId());
        entity.setStatus(order.getStatus());
        entity.setTotalAmount(order.getTotalAmount());
        entity.setShippingAddress(order.getShippingAddress());
        entity.setNotes(order.getNotes());

        if (order.getItems() != null) {
            order.getItems().forEach(item -> {
                OrderItemJpaEntity itemEntity = toOrderItemJpaEntity(item);
                entity.addItem(itemEntity);
            });
        }

        return entity;
    }

    /**
     * Updates an existing JPA entity with values from a domain model.
     *
     * @param entity the existing JPA entity to update
     * @param order  the domain Order model with new values
     */
    public void updateJpaEntity(OrderJpaEntity entity, Order order) {
        entity.setStatus(order.getStatus());
        entity.setTotalAmount(order.getTotalAmount());
        entity.setShippingAddress(order.getShippingAddress());
        entity.setNotes(order.getNotes());
    }

    /**
     * Converts an OrderItemJpaEntity to OrderItem domain model.
     *
     * @param entity the JPA entity to convert
     * @return the domain OrderItem model
     */
    private OrderItem toOrderItemDomain(OrderItemJpaEntity entity) {
        OrderItem item = new OrderItem();
        item.setId(entity.getId());
        item.setProductId(entity.getProductId());
        item.setProductName(entity.getProductName());
        item.setProductSku(entity.getProductSku());
        item.setQuantity(entity.getQuantity());
        item.setUnitPrice(entity.getUnitPrice());
        item.setTotalPrice(entity.getTotalPrice());
        return item;
    }

    /**
     * Converts an OrderItem domain model to OrderItemJpaEntity.
     *
     * @param item the domain model to convert
     * @return the JPA entity
     */
    private OrderItemJpaEntity toOrderItemJpaEntity(OrderItem item) {
        OrderItemJpaEntity entity = new OrderItemJpaEntity();
        entity.setId(item.getId());
        entity.setProductId(item.getProductId());
        entity.setProductName(item.getProductName());
        entity.setProductSku(item.getProductSku());
        entity.setQuantity(item.getQuantity());
        entity.setUnitPrice(item.getUnitPrice());
        entity.setTotalPrice(item.getTotalPrice());
        return entity;
    }
}
