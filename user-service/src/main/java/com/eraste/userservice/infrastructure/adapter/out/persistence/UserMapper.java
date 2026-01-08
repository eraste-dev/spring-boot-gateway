package com.eraste.userservice.infrastructure.adapter.out.persistence;

import com.eraste.userservice.domain.model.User;
import org.springframework.stereotype.Component;

/**
 * Mapper between domain User and JPA UserJpaEntity.
 * <p>
 * This class handles the conversion between the domain model ({@link User})
 * and the persistence model ({@link UserJpaEntity}), maintaining the separation
 * of concerns between domain and infrastructure layers.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see User
 * @see UserJpaEntity
 */
@Component
public class UserMapper {

    /**
     * Converts a JPA entity to a domain model.
     * <p>
     * Maps all fields including audit timestamps (createdAt, updatedAt).
     * </p>
     *
     * @param entity the JPA entity to convert
     * @return the domain User model, or null if entity is null
     */
    public User toDomain(UserJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        User user = new User();
        user.setId(entity.getId());
        user.setUsername(entity.getUsername());
        user.setEmail(entity.getEmail());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setCreatedAt(entity.getCreatedAt());
        user.setUpdatedAt(entity.getUpdatedAt());
        return user;
    }

    /**
     * Converts a domain model to a JPA entity.
     * <p>
     * Note: Timestamps are managed by JPA/Hibernate, not copied from domain.
     * </p>
     *
     * @param user the domain User model to convert
     * @return the JPA entity, or null if user is null
     */
    public UserJpaEntity toJpaEntity(User user) {
        if (user == null) {
            return null;
        }
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        return entity;
    }

    /**
     * Updates an existing JPA entity with values from a domain model.
     * <p>
     * Used for update operations to preserve the existing entity's ID and timestamps.
     * </p>
     *
     * @param entity the existing JPA entity to update
     * @param user   the domain User model with new values
     */
    public void updateJpaEntity(UserJpaEntity entity, User user) {
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
    }
}
