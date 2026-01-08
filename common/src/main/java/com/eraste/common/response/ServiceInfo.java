package com.eraste.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO representing service information for the root endpoint.
 * <p>
 * This class provides a standardized response for the root path (/)
 * of each microservice, displaying service metadata and available endpoints.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceInfo {

    /** Name of the service. */
    private String service;

    /** Current version of the service. */
    private String version;

    /** Brief description of the service. */
    private String description;

    /** Current status of the service. */
    private String status;

    /** Timestamp when the response was generated. */
    private LocalDateTime timestamp;

    /** Map of available endpoints with their descriptions. */
    private Map<String, String> endpoints;

    /**
     * Default constructor initializing timestamp.
     */
    public ServiceInfo() {
        this.timestamp = LocalDateTime.now();
        this.status = "UP";
    }

    /**
     * Constructs ServiceInfo with basic information.
     *
     * @param service     the service name
     * @param version     the service version
     * @param description the service description
     */
    public ServiceInfo(String service, String version, String description) {
        this();
        this.service = service;
        this.version = version;
        this.description = description;
    }

    /**
     * Builder method to set endpoints.
     *
     * @param endpoints map of endpoint paths to descriptions
     * @return this ServiceInfo instance
     */
    public ServiceInfo withEndpoints(Map<String, String> endpoints) {
        this.endpoints = endpoints;
        return this;
    }

    // Getters and Setters

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(Map<String, String> endpoints) {
        this.endpoints = endpoints;
    }
}
