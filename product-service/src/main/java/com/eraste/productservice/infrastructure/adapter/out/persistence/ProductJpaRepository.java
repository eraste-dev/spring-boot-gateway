package com.eraste.productservice.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Product entities.
 * <p>
 * This interface provides CRUD operations and custom queries for {@link ProductJpaEntity}.
 * Spring Data JPA automatically generates the implementation at runtime.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see JpaRepository
 * @see ProductJpaEntity
 */
@Repository
public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {

    /**
     * Finds a product by its SKU.
     *
     * @param sku the SKU to search for
     * @return an Optional containing the product entity if found
     */
    Optional<ProductJpaEntity> findBySku(String sku);

    /**
     * Checks if a product exists with the given SKU.
     *
     * @param sku the SKU to check
     * @return true if a product exists with this SKU
     */
    boolean existsBySku(String sku);

    /**
     * Finds all active products.
     *
     * @return a list of active product entities
     */
    List<ProductJpaEntity> findByActiveTrue();

    /**
     * Finds products by category ID.
     *
     * @param categoryId the category ID to filter by
     * @return a list of product entities in the category
     */
    List<ProductJpaEntity> findByCategoryId(Long categoryId);
}
