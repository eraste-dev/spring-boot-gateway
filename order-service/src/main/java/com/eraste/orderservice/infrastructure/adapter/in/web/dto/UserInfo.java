package com.eraste.orderservice.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object containing user information for order responses.
 * <p>
 * This DTO represents a simplified view of user data fetched from user-service.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@Schema(description = "User information in order response")
public class UserInfo {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "Username", example = "johndoe")
    private String username;

    @Schema(description = "Email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "First name", example = "John")
    private String firstName;

    @Schema(description = "Last name", example = "Doe")
    private String lastName;

    @Schema(description = "Full name", example = "John Doe")
    private String fullName;

    public UserInfo() {
    }

    public UserInfo(Long id, String username, String email, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = (firstName != null ? firstName : "") +
                        (lastName != null ? " " + lastName : "");
        this.fullName = this.fullName.trim();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        updateFullName();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        updateFullName();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    private void updateFullName() {
        this.fullName = (firstName != null ? firstName : "") +
                        (lastName != null ? " " + lastName : "");
        this.fullName = this.fullName.trim();
    }
}
