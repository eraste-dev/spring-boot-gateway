package com.eraste.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main application class for Order Service.
 * <p>
 * This class bootstraps the Spring Boot application for the order microservice.
 * It scans both the order service package and the common module for components.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.eraste.orderservice", "com.eraste.common"})
public class OrderServiceApplication {

    /**
     * Main entry point for the Order Service application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
