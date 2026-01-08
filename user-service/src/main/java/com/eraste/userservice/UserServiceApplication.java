package com.eraste.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main entry point for the User Service microservice.
 * <p>
 * This Spring Boot application provides REST APIs for user management
 * following the hexagonal architecture pattern.
 * </p>
 * <p>
 * Features:
 * <ul>
 *   <li>CRUD operations for users</li>
 *   <li>H2 in-memory database (development)</li>
 *   <li>OpenAPI/Swagger documentation</li>
 *   <li>Spring Actuator for health monitoring</li>
 * </ul>
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.eraste.userservice", "com.eraste.common"})
public class UserServiceApplication {

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
