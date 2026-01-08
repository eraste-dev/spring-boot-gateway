package com.eraste.userservice.domain.port.in;

import com.eraste.userservice.domain.model.User;
import java.util.List;
import java.util.Optional;

/**
 * Input port defining the use cases for User operations.
 * <p>
 * This interface follows the Hexagonal Architecture (Ports and Adapters) pattern.
 * It defines the primary/driving port that the application exposes to the outside world.
 * The implementation is provided by the application service layer.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
public interface UserUseCase {

    /**
     * Creates a new user in the system.
     *
     * @param user the user to create (without ID)
     * @return the created user with generated ID and timestamps
     * @throws IllegalArgumentException if username or email already exists
     */
    User createUser(User user);

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return an Optional containing the user if found, empty otherwise
     */
    Optional<User> getUserById(Long id);

    /**
     * Retrieves all users from the system.
     *
     * @return a list of all users, empty list if no users exist
     */
    List<User> getAllUsers();

    /**
     * Updates an existing user with new information.
     *
     * @param id   the unique identifier of the user to update
     * @param user the user data to update
     * @return the updated user
     * @throws com.eraste.common.exception.ResourceNotFoundException if user not found
     */
    User updateUser(Long id, User user);

    /**
     * Deletes a user from the system.
     *
     * @param id the unique identifier of the user to delete
     * @throws com.eraste.common.exception.ResourceNotFoundException if user not found
     */
    void deleteUser(Long id);

    /**
     * Retrieves a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the user if found, empty otherwise
     */
    Optional<User> getUserByUsername(String username);

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the user if found, empty otherwise
     */
    Optional<User> getUserByEmail(String email);
}
