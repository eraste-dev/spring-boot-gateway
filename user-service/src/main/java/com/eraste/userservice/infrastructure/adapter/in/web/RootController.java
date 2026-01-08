package com.eraste.userservice.infrastructure.adapter.in.web;

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

    @Value("${spring.application.name:user-service}")
    private String serviceName;

    @Value("${server.port:8081}")
    private String serverPort;

    /**
     * Returns service information and available endpoints.
     *
     * @return ResponseEntity containing ServiceInfo
     */
    @GetMapping("/")
    public ResponseEntity<ServiceInfo> getServiceInfo() {
        Map<String, String> endpoints = new LinkedHashMap<>();
        endpoints.put("GET /users", "Get all users");
        endpoints.put("GET /users/{id}", "Get user by ID");
        endpoints.put("POST /users", "Create a new user");
        endpoints.put("PUT /users/{id}", "Update a user");
        endpoints.put("DELETE /users/{id}", "Delete a user");
        endpoints.put("GET /swagger-ui.html", "Swagger UI documentation");
        endpoints.put("GET /api-docs", "OpenAPI specification (JSON)");
        endpoints.put("GET /actuator/health", "Health check endpoint");

        ServiceInfo serviceInfo = new ServiceInfo(
                serviceName,
                "1.0.0",
                "Microservice for user management"
        ).withEndpoints(endpoints);

        return ResponseEntity.ok(serviceInfo);
    }
}
