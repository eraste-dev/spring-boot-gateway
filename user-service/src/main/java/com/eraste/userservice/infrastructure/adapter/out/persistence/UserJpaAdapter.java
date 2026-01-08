package com.eraste.userservice.infrastructure.adapter.out.persistence;

import com.eraste.userservice.domain.model.User;
import com.eraste.userservice.domain.port.out.UserRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JPA Adapter implementing the UserRepositoryPort.
 * <p>
 * This class serves as the secondary adapter (driven adapter) in the hexagonal architecture.
 * It implements the {@link UserRepositoryPort} output port using Spring Data JPA.
 * </p>
 * <p>
 * This adapter can be swapped for another implementation (MongoDB, Cassandra, etc.)
 * without affecting the domain layer, as long as it implements the same port interface.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see UserRepositoryPort
 * @see UserJpaRepository
 * @see UserMapper
 */
@Component
public class UserJpaAdapter implements UserRepositoryPort {

    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

    /**
     * Constructs a UserJpaAdapter with required dependencies.
     *
     * @param jpaRepository the Spring Data JPA repository
     * @param mapper        the mapper for domain/entity conversion
     */
    public UserJpaAdapter(UserJpaRepository jpaRepository, UserMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Handles both create and update operations. For updates, it loads the existing
     * entity to preserve audit fields and then applies changes.
     * </p>
     */
    @Override
    public User save(User user) {
        UserJpaEntity entity;
        if (user.getId() != null) {
            // Update existing entity
            entity = jpaRepository.findById(user.getId())
                    .orElse(mapper.toJpaEntity(user));
            mapper.updateJpaEntity(entity, user);
        } else {
            // Create new entity
            entity = mapper.toJpaEntity(user);
        }
        UserJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepository.findByUsername(username)
                .map(mapper::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
