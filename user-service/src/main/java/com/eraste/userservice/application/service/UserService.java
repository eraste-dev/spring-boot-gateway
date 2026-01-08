package com.eraste.userservice.application.service;

import com.eraste.common.exception.ResourceNotFoundException;
import com.eraste.userservice.domain.model.User;
import com.eraste.userservice.domain.port.in.UserUseCase;
import com.eraste.userservice.domain.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Application service implementing user use cases.
 * <p>
 * This service acts as the orchestrator between the domain layer and infrastructure.
 * It implements the {@link UserUseCase} input port and uses the {@link UserRepositoryPort}
 * output port for persistence operations.
 * </p>
 * <p>
 * All methods are transactional by default. Read operations use read-only transactions
 * for better performance.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see UserUseCase
 * @see UserRepositoryPort
 */
@Service
@Transactional
public class UserService implements UserUseCase {

    private final UserRepositoryPort userRepository;

    /**
     * Constructs a UserService with the required repository port.
     *
     * @param userRepository the repository port for user persistence operations
     */
    public UserService(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Validates that username and email are unique before creating the user.
     * </p>
     *
     * @throws IllegalArgumentException if username or email already exists
     */
    @Override
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Updates all user fields except ID and timestamps.
     * </p>
     *
     * @throws ResourceNotFoundException if no user exists with the given ID
     */
    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());

        return userRepository.save(existingUser);
    }

    /**
     * {@inheritDoc}
     *
     * @throws ResourceNotFoundException if no user exists with the given ID
     */
    @Override
    public void deleteUser(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        userRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
