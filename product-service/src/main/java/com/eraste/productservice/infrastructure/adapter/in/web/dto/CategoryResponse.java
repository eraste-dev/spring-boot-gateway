package com.eraste.productservice.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for category API responses.
 * <p>
 * This DTO represents the category data returned by the REST API.
 * It includes all category attributes and audit timestamps.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@Schema(description = "Response object containing category information")
public class CategoryResponse {

    /** Unique identifier of the category. */
    @Schema(description = "Unique identifier of the category", example = "1")
    private Long id;

    /** Category name. */
    @Schema(description = "Category name", example = "Electronics")
    private String name;

    /** Category description. */
    @Schema(description = "Category description", example = "Electronic devices and accessories")
    private String description;

    /** Category code. */
    @Schema(description = "Unique category code", example = "ELEC")
    private String code;

    /** Active status. */
    @Schema(description = "Whether the category is active", example = "true")
    private Boolean active;

    /** Timestamp when the category was created. */
    @Schema(description = "Timestamp when category was created", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;

    /** Timestamp when the category was last updated. */
    @Schema(description = "Timestamp when category was last updated", example = "2024-01-15T10:30:00")
    private LocalDateTime updatedAt;

    /**
     * Default constructor required for JSON serialization.
     */
    public CategoryResponse() {
    }

    /**
     * Constructs a CategoryResponse with all fields.
     *
     * @param id          the unique identifier
     * @param name        the category name
     * @param description the category description
     * @param code        the category code
     * @param active      the active status
     * @param createdAt   the creation timestamp
     * @param updatedAt   the last update timestamp
     */
    public CategoryResponse(Long id, String name, String description, String code,
                            Boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.code = code;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Gets the category ID.
     *
     * @return the category ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the category ID.
     *
     * @param id the category ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the category name.
     *
     * @return the category name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the category name.
     *
     * @param name the category name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the category description.
     *
     * @return the category description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the category description.
     *
     * @param description the category description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the category code.
     *
     * @return the category code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the category code.
     *
     * @param code the category code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the active status.
     *
     * @return true if the category is active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Sets the active status.
     *
     * @param active the active status to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Gets the creation timestamp.
     *
     * @return the creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp.
     *
     * @param createdAt the creation timestamp to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the last update timestamp.
     *
     * @return the last update timestamp
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the last update timestamp.
     *
     * @param updatedAt the last update timestamp to set
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
