package com.eraste.productservice.domain.model;

import java.time.LocalDateTime;

/**
 * Domain entity representing a Product Category in the system.
 * <p>
 * This class is a pure domain model, independent of any infrastructure concerns
 * (database, framework, etc.). It contains the core business attributes and
 * behaviors related to a product category.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
public class Category {

    /** Unique identifier of the category. */
    private Long id;

    /** Category name. */
    private String name;

    /** Category description. */
    private String description;

    /** Category code (unique identifier for business use). */
    private String code;

    /** Whether the category is active. */
    private Boolean active;

    /** Timestamp when the category was created. */
    private LocalDateTime createdAt;

    /** Timestamp when the category was last updated. */
    private LocalDateTime updatedAt;

    /**
     * Default constructor required for frameworks.
     */
    public Category() {
        this.active = true;
    }

    /**
     * Constructs a Category with the specified attributes.
     *
     * @param id          the unique identifier
     * @param name        the category name
     * @param description the category description
     * @param code        the category code
     */
    public Category(Long id, String name, String description, String code) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.code = code;
        this.active = true;
    }

    /**
     * Gets the unique identifier of the category.
     *
     * @return the category ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the category.
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
