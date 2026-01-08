package com.eraste.userservice.domain.model;

import java.time.LocalDateTime;

/**
 * Domain entity representing a User in the system.
 * <p>
 * This class is a pure domain model, independent of any infrastructure concerns
 * (database, framework, etc.). It contains the core business attributes and
 * behaviors related to a user.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
public class User {

    /** Unique identifier of the user. */
    private Long id;

    /** Unique username for authentication and identification. */
    private String username;

    /** User's email address, must be unique in the system. */
    private String email;

    /** User's first name. */
    private String firstName;

    /** User's last name. */
    private String lastName;

    /** Timestamp when the user was created. */
    private LocalDateTime createdAt;

    /** Timestamp when the user was last updated. */
    private LocalDateTime updatedAt;

    /**
     * Default constructor required for frameworks.
     */
    public User() {
    }

    /**
     * Constructs a User with the specified attributes.
     *
     * @param id        the unique identifier
     * @param username  the unique username
     * @param email     the email address
     * @param firstName the first name
     * @param lastName  the last name
     */
    public User(Long id, String username, String email, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return the user ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
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

    /**
     * Gets the full name by concatenating first and last name.
     *
     * @return the full name (firstName + lastName)
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
