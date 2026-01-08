package com.eraste.productservice.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for category creation and update requests.
 * <p>
 * This DTO is used as the request body for POST and PUT operations
 * on the Category REST API. It includes validation constraints to ensure
 * data integrity before processing.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@Schema(description = "Request object for creating or updating a category")
public class CategoryRequest {

    /** Category name (3-100 characters). */
    @Schema(description = "Category name", example = "Electronics", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    /** Category description (max 500 characters). */
    @Schema(description = "Category description", example = "Electronic devices and accessories")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    /** Unique category code (3-50 characters). */
    @Schema(description = "Unique category code", example = "ELEC", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Code is required")
    @Size(min = 2, max = 50, message = "Code must be between 2 and 50 characters")
    private String code;

    /** Active status. */
    @Schema(description = "Whether the category is active", example = "true")
    private Boolean active = true;

    /**
     * Default constructor required for JSON deserialization.
     */
    public CategoryRequest() {
    }

    /**
     * Constructs a CategoryRequest with all fields.
     *
     * @param name        the category name
     * @param description the category description
     * @param code        the category code
     * @param active      the active status
     */
    public CategoryRequest(String name, String description, String code, Boolean active) {
        this.name = name;
        this.description = description;
        this.code = code;
        this.active = active;
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
}
