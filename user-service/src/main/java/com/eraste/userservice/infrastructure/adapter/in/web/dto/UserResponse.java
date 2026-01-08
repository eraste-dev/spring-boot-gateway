package com.eraste.userservice.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for user API responses.
 * <p>
 * This DTO represents the user data returned by the REST API.
 * It includes all user attributes plus computed fields like fullName
 * and audit timestamps.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@Schema(description = "Response object containing user information")
public class UserResponse {

    /** Unique identifier of the user. */
    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    /** Unique username. */
    @Schema(description = "Unique username", example = "john_doe")
    private String username;

    /** User's email address. */
    @Schema(description = "User email address", example = "john.doe@example.com")
    private String email;

    /** User's first name. */
    @Schema(description = "User first name", example = "John")
    private String firstName;

    /** User's last name. */
    @Schema(description = "User last name", example = "Doe")
    private String lastName;

    /** Computed full name (firstName + lastName). */
    @Schema(description = "Full name (first + last)", example = "John Doe")
    private String fullName;

    /** Timestamp when the user was created. */
    @Schema(description = "Timestamp when user was created", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;

    /** Timestamp when the user was last updated. */
    @Schema(description = "Timestamp when user was last updated", example = "2024-01-15T10:30:00")
    private LocalDateTime updatedAt;

    /**
     * Default constructor required for JSON serialization.
     */
    public UserResponse() {
    }

    /**
     * Constructs a UserResponse with all fields.
     * <p>
     * The fullName is automatically computed from firstName and lastName.
     * </p>
     *
     * @param id        the unique identifier
     * @param username  the username
     * @param email     the email address
     * @param firstName the first name
     * @param lastName  the last name
     * @param createdAt the creation timestamp
     * @param updatedAt the last update timestamp
     */
    public UserResponse(Long id, String username, String email, String firstName,
                        String lastName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + " " + lastName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Gets the user ID.
     *
     * @return the user ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the user ID.
     *
     * @param id the user ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the full name.
     *
     * @return the full name (firstName + lastName)
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the full name.
     *
     * @param fullName the full name to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
