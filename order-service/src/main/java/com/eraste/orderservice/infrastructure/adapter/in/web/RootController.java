package com.eraste.orderservice.infrastructure.adapter.in.web;

import com.eraste.common.response.ServiceInfo;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Root controller providing service information at the base path.
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@Hidden
public class RootController {

    @Value("${spring.application.name:order-service}")
    private String serviceName;

    @Value("${server.port:8083}")
    private String serverPort;

    @GetMapping("/")
    public ResponseEntity<ServiceInfo> getServiceInfo() {
        Map<String, String> endpoints = new LinkedHashMap<>();
        endpoints.put("GET /orders", "Get all orders");
        endpoints.put("GET /orders/{id}", "Get order by ID");
        endpoints.put("GET /orders/number/{orderNumber}", "Get order by order number");
        endpoints.put("GET /orders/user/{userId}", "Get orders by user ID");
        endpoints.put("GET /orders/status/{status}", "Get orders by status");
        endpoints.put("POST /orders", "Create a new order");
        endpoints.put("PATCH /orders/{id}/status", "Update order status");
        endpoints.put("POST /orders/{id}/cancel", "Cancel an order");
        endpoints.put("DELETE /orders/{id}", "Delete an order");
        endpoints.put("GET /swagger-ui.html", "Swagger UI documentation");
        endpoints.put("GET /api-docs", "OpenAPI specification (JSON)");
        endpoints.put("GET /actuator/health", "Health check endpoint");

        ServiceInfo serviceInfo = new ServiceInfo(
                serviceName,
                "1.0.0",
                "Microservice for order management"
        ).withEndpoints(endpoints);

        return ResponseEntity.ok(serviceInfo);
    }
}
