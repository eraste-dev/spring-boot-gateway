package com.eraste.userservice.infrastructure.adapter.in.web;

import com.eraste.common.exception.ResourceNotFoundException;
import com.eraste.common.response.ApiResponse;
import com.eraste.userservice.domain.model.User;
import com.eraste.userservice.domain.port.in.UserUseCase;
import com.eraste.userservice.infrastructure.adapter.in.web.dto.UserRequest;
import com.eraste.userservice.infrastructure.adapter.in.web.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for User operations.
 * <p>
 * This controller serves as the primary adapter (driving adapter) in the hexagonal architecture.
 * It handles HTTP requests and delegates business logic to the {@link UserUseCase} port.
 * </p>
 * <p>
 * All endpoints return responses wrapped in {@link ApiResponse} for consistent API responses.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see UserUseCase
 */
@Tag(name = "Users", description = "User management API")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserUseCase userUseCase;

    /**
     * Constructs a UserController with the required use case.
     *
     * @param userUseCase the use case for user operations
     */
    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @Operation(summary = "Create a new user", description = "Creates a new user with the provided information")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Username or email already exists")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody @Parameter(description = "User data to create") UserRequest request) {
        User user = mapToUser(request);
        User createdUser = userUseCase.createUser(user);
        UserResponse response = mapToResponse(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(response));
    }

    @Operation(summary = "Get user by ID", description = "Returns a user based on the provided ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @Parameter(description = "User ID", required = true) @PathVariable Long id) {
        User user = userUseCase.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        UserResponse response = mapToResponse(user);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Get all users", description = "Returns a list of all users")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userUseCase.getAllUsers().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @Operation(summary = "Update user", description = "Updates an existing user with the provided information")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @Parameter(description = "User ID", required = true) @PathVariable Long id,
            @Valid @RequestBody @Parameter(description = "Updated user data") UserRequest request) {
        User user = mapToUser(request);
        User updatedUser = userUseCase.updateUser(id, user);
        UserResponse response = mapToResponse(updatedUser);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", response));
    }

    @Operation(summary = "Delete user", description = "Deletes a user based on the provided ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @Parameter(description = "User ID", required = true) @PathVariable Long id) {
        userUseCase.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }

    /**
     * Maps a UserRequest DTO to a domain User entity.
     *
     * @param request the request DTO
     * @return the domain User entity
     */
    private User mapToUser(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return user;
    }

    /**
     * Maps a domain User entity to a UserResponse DTO.
     *
     * @param user the domain User entity
     * @return the response DTO
     */
    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
