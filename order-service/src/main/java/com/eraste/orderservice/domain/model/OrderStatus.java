package com.eraste.orderservice.domain.model;

/**
 * Enumeration representing the possible states of an order.
 * <p>
 * Orders follow a lifecycle from PENDING through various states
 * until they reach a terminal state (DELIVERED, CANCELLED, or REFUNDED).
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
public enum OrderStatus {

    /** Order has been created but not yet confirmed. */
    PENDING,

    /** Order has been confirmed and payment received. */
    CONFIRMED,

    /** Order is being prepared for shipment. */
    PROCESSING,

    /** Order has been shipped. */
    SHIPPED,

    /** Order has been delivered to the customer. */
    DELIVERED,

    /** Order has been cancelled. */
    CANCELLED,

    /** Order has been refunded. */
    REFUNDED
}
