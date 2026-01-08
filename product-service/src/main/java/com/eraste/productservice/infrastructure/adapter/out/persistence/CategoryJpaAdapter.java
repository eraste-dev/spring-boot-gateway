package com.eraste.productservice.infrastructure.adapter.out.persistence;

import com.eraste.productservice.domain.model.Category;
import com.eraste.productservice.domain.port.out.CategoryRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JPA Adapter implementing the CategoryRepositoryPort.
 * <p>
 * This class serves as the secondary adapter (driven adapter) in the hexagonal architecture.
 * It implements the {@link CategoryRepositoryPort} output port using Spring Data JPA.
 * </p>
 * <p>
 * This adapter can be swapped for another implementation (MongoDB, Cassandra, etc.)
 * without affecting the domain layer, as long as it implements the same port interface.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see CategoryRepositoryPort
 * @see CategoryJpaRepository
 * @see CategoryMapper
 */
@Component
public class CategoryJpaAdapter implements CategoryRepositoryPort {

    private final CategoryJpaRepository jpaRepository;
    private final CategoryMapper mapper;

    /**
     * Constructs a CategoryJpaAdapter with required dependencies.
     *
     * @param jpaRepository the Spring Data JPA repository
     * @param mapper        the mapper for domain/entity conversion
     */
    public CategoryJpaAdapter(CategoryJpaRepository jpaRepository, CategoryMapper mapper) {
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
    public Category save(Category category) {
        CategoryJpaEntity entity;
        if (category.getId() != null) {
            entity = jpaRepository.findById(category.getId())
                    .orElse(mapper.toJpaEntity(category));
            mapper.updateJpaEntity(entity, category);
        } else {
            entity = mapper.toJpaEntity(category);
        }
        CategoryJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Category> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> findByActiveTrue() {
        return jpaRepository.findByActiveTrue().stream()
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
    public Optional<Category> findByCode(String code) {
        return jpaRepository.findByCode(code)
                .map(mapper::toDomain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsByCode(String code) {
        return jpaRepository.existsByCode(code);
    }
}
