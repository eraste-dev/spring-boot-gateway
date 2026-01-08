package com.eraste.userservice.domain.port.out;

import com.eraste.userservice.domain.model.User;
import java.util.List;
import java.util.Optional;

/**
 * Output port for User persistence operations.
 * <p>
 * This interface follows the Hexagonal Architecture (Ports and Adapters) pattern.
 * It defines the secondary/driven port for persistence operations.
 * Implementations are provided by infrastructure adapters (JPA, MongoDB, etc.),
 * allowing the domain to remain independent of persistence technology.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
public interface UserRepositoryPort {

    /**
     * Saves a user entity (create or update).
     *
     * @param user the user to save
     * @return the saved user with updated fields (ID, timestamps)
     */
    User save(User user);

    /**
     * Finds a user by their unique identifier.
     *
     * @param id the unique identifier
     * @return an Optional containing the user if found, empty otherwise
     */
    Optional<User> findById(Long id);

    /**
     * Retrieves all users from the repository.
     *
     * @return a list of all users
     */
    List<User> findAll();

    /**
     * Deletes a user by their unique identifier.
     *
     * @param id the unique identifier of the user to delete
     */
    void deleteById(Long id);

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the user if found, empty otherwise
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by their email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the user if found, empty otherwise
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if a user exists with the given username.
     *
     * @param username the username to check
     * @return true if a user with this username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Checks if a user exists with the given email.
     *
     * @param email the email to check
     * @return true if a user with this email exists, false otherwise
     */
    boolean existsByEmail(String email);
}
