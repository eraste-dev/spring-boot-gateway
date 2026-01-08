package com.eraste.orderservice.infrastructure.adapter.in.web;

import com.eraste.common.exception.ResourceNotFoundException;
import com.eraste.common.response.ApiResponse;
import com.eraste.orderservice.domain.model.Order;
import com.eraste.orderservice.domain.model.OrderItem;
import com.eraste.orderservice.domain.model.OrderStatus;
import com.eraste.orderservice.domain.port.in.OrderUseCase;
import com.eraste.orderservice.infrastructure.adapter.in.web.dto.*;
import com.eraste.orderservice.infrastructure.client.UserServiceClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Order operations.
 * <p>
 * This controller serves as the primary adapter (driving adapter) in the hexagonal architecture.
 * It handles HTTP requests and delegates business logic to the {@link OrderUseCase} port.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see OrderUseCase
 */
@Tag(name = "Orders", description = "Order management API")
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderUseCase orderUseCase;
    private final UserServiceClient userServiceClient;

    public OrderController(OrderUseCase orderUseCase, UserServiceClient userServiceClient) {
        this.orderUseCase = orderUseCase;
        this.userServiceClient = userServiceClient;
    }

    @Operation(summary = "Create a new order", description = "Creates a new order with the provided items")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Order created successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @Valid @RequestBody @Parameter(description = "Order data") OrderRequest request) {
        Order order = mapToOrder(request);
        Order createdOrder = orderUseCase.createOrder(order);
        OrderResponse response = mapToResponse(createdOrder);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(response));
    }

    @Operation(summary = "Get order by ID", description = "Returns an order based on the provided ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Order found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(
            @Parameter(description = "Order ID", required = true) @PathVariable Long id) {
        Order order = orderUseCase.getOrderById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        OrderResponse response = mapToResponse(order);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Get order by order number", description = "Returns an order based on the order number")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Order found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderByOrderNumber(
            @Parameter(description = "Order number", required = true) @PathVariable String orderNumber) {
        Order order = orderUseCase.getOrderByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderNumber", orderNumber));
        OrderResponse response = mapToResponse(order);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Get all orders", description = "Returns a list of all orders")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of orders retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        List<OrderResponse> orders = orderUseCase.getAllOrders().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    @Operation(summary = "Get orders by user", description = "Returns all orders for a specific user")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of orders retrieved successfully")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByUserId(
            @Parameter(description = "User ID", required = true) @PathVariable Long userId) {
        List<OrderResponse> orders = orderUseCase.getOrdersByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    @Operation(summary = "Get orders by status", description = "Returns all orders with a specific status")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of orders retrieved successfully")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByStatus(
            @Parameter(description = "Order status", required = true) @PathVariable OrderStatus status) {
        List<OrderResponse> orders = orderUseCase.getOrdersByStatus(status).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    @Operation(summary = "Update order status", description = "Updates the status of an existing order")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Order status updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid status transition"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
            @Parameter(description = "Order ID", required = true) @PathVariable Long id,
            @Valid @RequestBody @Parameter(description = "New status") StatusUpdateRequest request) {
        Order updatedOrder = orderUseCase.updateOrderStatus(id, request.getStatus());
        OrderResponse response = mapToResponse(updatedOrder);
        return ResponseEntity.ok(ApiResponse.success("Order status updated successfully", response));
    }

    @Operation(summary = "Cancel order", description = "Cancels an order if it can be cancelled")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Order cancelled successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Order cannot be cancelled"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(
            @Parameter(description = "Order ID", required = true) @PathVariable Long id) {
        Order cancelledOrder = orderUseCase.cancelOrder(id);
        OrderResponse response = mapToResponse(cancelledOrder);
        return ResponseEntity.ok(ApiResponse.success("Order cancelled successfully", response));
    }

    @Operation(summary = "Delete order", description = "Deletes an order from the system")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Order deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Order not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(
            @Parameter(description = "Order ID", required = true) @PathVariable Long id) {
        orderUseCase.deleteOrder(id);
        return ResponseEntity.ok(ApiResponse.success("Order deleted successfully", null));
    }

    private Order mapToOrder(OrderRequest request) {
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setShippingAddress(request.getShippingAddress());
        order.setNotes(request.getNotes());

        if (request.getItems() != null) {
            List<OrderItem> items = request.getItems().stream()
                    .map(this::mapToOrderItem)
                    .collect(Collectors.toList());
            order.setItems(items);
        }

        return order;
    }

    private OrderItem mapToOrderItem(OrderItemRequest request) {
        return new OrderItem(
                request.getProductId(),
                request.getProductName(),
                request.getProductSku(),
                request.getQuantity(),
                request.getUnitPrice()
        );
    }

    private OrderResponse mapToResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getId(),
                        item.getProductId(),
                        item.getProductName(),
                        item.getProductSku(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getTotalPrice()
                ))
                .collect(Collectors.toList());

        OrderResponse response = new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getUserId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getShippingAddress(),
                order.getNotes(),
                itemResponses,
                order.getCreatedAt(),
                order.getUpdatedAt()
        );

        // Fetch user information from user-service
        if (order.getUserId() != null) {
            userServiceClient.getUserById(order.getUserId())
                    .ifPresent(response::setUser);
        }

        return response;
    }
}
