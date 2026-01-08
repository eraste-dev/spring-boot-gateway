package com.eraste.productservice.application.service;

import com.eraste.common.exception.ResourceNotFoundException;
import com.eraste.productservice.domain.model.Category;
import com.eraste.productservice.domain.port.in.CategoryUseCase;
import com.eraste.productservice.domain.port.out.CategoryRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Application service implementing category use cases.
 * <p>
 * This service acts as the orchestrator between the domain layer and infrastructure.
 * It implements the {@link CategoryUseCase} input port and uses the {@link CategoryRepositoryPort}
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
 * @see CategoryUseCase
 * @see CategoryRepositoryPort
 */
@Service
@Transactional
public class CategoryService implements CategoryUseCase {

    private final CategoryRepositoryPort categoryRepository;

    /**
     * Constructs a CategoryService with the required repository port.
     *
     * @param categoryRepository the repository port for category persistence operations
     */
    public CategoryService(CategoryRepositoryPort categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Validates that code is unique before creating the category.
     * </p>
     *
     * @throws IllegalArgumentException if code already exists
     */
    @Override
    public Category createCategory(Category category) {
        if (categoryRepository.existsByCode(category.getCode())) {
            throw new IllegalArgumentException("Category code already exists: " + category.getCode());
        }
        return categoryRepository.save(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Category> getActiveCategories() {
        return categoryRepository.findByActiveTrue();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Updates all category fields except ID and timestamps.
     * </p>
     *
     * @throws ResourceNotFoundException if no category exists with the given ID
     */
    @Override
    public Category updateCategory(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        existingCategory.setCode(category.getCode());
        existingCategory.setActive(category.getActive());

        return categoryRepository.save(existingCategory);
    }

    /**
     * {@inheritDoc}
     *
     * @throws ResourceNotFoundException if no category exists with the given ID
     */
    @Override
    public void deleteCategory(Long id) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Category", "id", id);
        }
        categoryRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryByCode(String code) {
        return categoryRepository.findByCode(code);
    }
}
