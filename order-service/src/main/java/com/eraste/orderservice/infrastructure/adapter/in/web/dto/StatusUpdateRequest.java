package com.eraste.orderservice.infrastructure.adapter.in.web.dto;

import com.eraste.orderservice.domain.model.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for order status update requests.
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@Schema(description = "Request object for updating order status")
public class StatusUpdateRequest {

    @Schema(description = "New order status", example = "CONFIRMED", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Status is required")
    private OrderStatus status;

    public StatusUpdateRequest() {
    }

    public StatusUpdateRequest(OrderStatus status) {
        this.status = status;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
