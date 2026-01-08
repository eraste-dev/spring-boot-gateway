package com.eraste.productservice.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Category entities.
 * <p>
 * This interface provides CRUD operations and custom queries for {@link CategoryJpaEntity}.
 * Spring Data JPA automatically generates the implementation at runtime.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see JpaRepository
 * @see CategoryJpaEntity
 */
@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryJpaEntity, Long> {

    /**
     * Finds a category by its code.
     *
     * @param code the code to search for
     * @return an Optional containing the category entity if found
     */
    Optional<CategoryJpaEntity> findByCode(String code);

    /**
     * Checks if a category exists with the given code.
     *
     * @param code the code to check
     * @return true if a category exists with this code
     */
    boolean existsByCode(String code);

    /**
     * Finds all active categories.
     *
     * @return a list of active category entities
     */
    List<CategoryJpaEntity> findByActiveTrue();
}
