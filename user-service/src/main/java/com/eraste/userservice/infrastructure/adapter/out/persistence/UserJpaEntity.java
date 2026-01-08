package com.eraste.userservice.infrastructure.adapter.out.persistence;

import com.eraste.common.entity.BaseEntity;
import jakarta.persistence.*;

/**
 * JPA Entity for User persistence.
 * <p>
 * This class represents the database schema for users. It extends {@link BaseEntity}
 * which provides common fields like id, createdAt, and updatedAt.
 * </p>
 * <p>
 * This is an infrastructure concern, completely separate from the domain model.
 * The {@link UserMapper} handles conversion between this entity and the domain model.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see BaseEntity
 * @see UserMapper
 */
@Entity
@Table(name = "users")
public class UserJpaEntity extends BaseEntity {

    /** Unique username (max 50 characters). */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /** Unique email address (max 100 characters). */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /** User's first name (max 50 characters). */
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    /** User's last name (max 50 characters). */
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    /**
     * Default constructor required by JPA.
     */
    public UserJpaEntity() {
    }

    /**
     * Constructs a UserJpaEntity with all required fields.
     *
     * @param username  the unique username
     * @param email     the unique email address
     * @param firstName the first name
     * @param lastName  the last name
     */
    public UserJpaEntity(String username, String email, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
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
}
