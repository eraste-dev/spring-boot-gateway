package com.eraste.productservice.infrastructure.adapter.in.web;

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
 * <p>
 * This controller handles requests to the root path (/) and returns
 * service metadata including available endpoints.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@Hidden // Hide from Swagger documentation
public class RootController {

    @Value("${spring.application.name:product-service}")
    private String serviceName;

    @Value("${server.port:8082}")
    private String serverPort;

    /**
     * Returns service information and available endpoints.
     *
     * @return ResponseEntity containing ServiceInfo
     */
    @GetMapping("/")
    public ResponseEntity<ServiceInfo> getServiceInfo() {
        Map<String, String> endpoints = new LinkedHashMap<>();
        // Product endpoints
        endpoints.put("GET /products", "Get all products");
        endpoints.put("GET /products/{id}", "Get product by ID");
        endpoints.put("GET /products/sku/{sku}", "Get product by SKU");
        endpoints.put("GET /products/active", "Get active products");
        endpoints.put("GET /products/category/{categoryId}", "Get products by category ID");
        endpoints.put("POST /products", "Create a new product");
        endpoints.put("PUT /products/{id}", "Update a product");
        endpoints.put("PATCH /products/{id}/stock", "Update product stock");
        endpoints.put("DELETE /products/{id}", "Delete a product");
        // Category endpoints
        endpoints.put("GET /categories", "Get all categories");
        endpoints.put("GET /categories/{id}", "Get category by ID");
        endpoints.put("GET /categories/code/{code}", "Get category by code");
        endpoints.put("GET /categories/active", "Get active categories");
        endpoints.put("POST /categories", "Create a new category");
        endpoints.put("PUT /categories/{id}", "Update a category");
        endpoints.put("DELETE /categories/{id}", "Delete a category");
        // Documentation
        endpoints.put("GET /swagger-ui.html", "Swagger UI documentation");
        endpoints.put("GET /api-docs", "OpenAPI specification (JSON)");
        endpoints.put("GET /actuator/health", "Health check endpoint");

        ServiceInfo serviceInfo = new ServiceInfo(
                serviceName,
                "1.0.0",
                "Microservice for product and category management"
        ).withEndpoints(endpoints);

        return ResponseEntity.ok(serviceInfo);
    }
}
