package com.eraste.userservice.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA Repository for User entities.
 * <p>
 * This interface provides CRUD operations and custom queries for {@link UserJpaEntity}.
 * Spring Data JPA automatically generates the implementation at runtime.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see JpaRepository
 * @see UserJpaEntity
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the user entity if found
     */
    Optional<UserJpaEntity> findByUsername(String username);

    /**
     * Finds a user by their email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the user entity if found
     */
    Optional<UserJpaEntity> findByEmail(String email);

    /**
     * Checks if a user exists with the given username.
     *
     * @param username the username to check
     * @return true if a user exists with this username
     */
    boolean existsByUsername(String username);

    /**
     * Checks if a user exists with the given email.
     *
     * @param email the email to check
     * @return true if a user exists with this email
     */
    boolean existsByEmail(String email);
}
