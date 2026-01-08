package com.eraste.productservice.infrastructure.adapter.in.web;

import com.eraste.common.exception.ResourceNotFoundException;
import com.eraste.common.response.ApiResponse;
import com.eraste.productservice.domain.model.Category;
import com.eraste.productservice.domain.port.in.CategoryUseCase;
import com.eraste.productservice.infrastructure.adapter.in.web.dto.CategoryRequest;
import com.eraste.productservice.infrastructure.adapter.in.web.dto.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Category operations.
 * <p>
 * This controller serves as the primary adapter (driving adapter) in the hexagonal architecture.
 * It handles HTTP requests and delegates business logic to the {@link CategoryUseCase} port.
 * </p>
 * <p>
 * All endpoints return responses wrapped in {@link ApiResponse} for consistent API responses.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see CategoryUseCase
 */
@Tag(name = "Categories", description = "Product category management API")
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryUseCase categoryUseCase;

    /**
     * Constructs a CategoryController with the required use case.
     *
     * @param categoryUseCase the use case for category operations
     */
    public CategoryController(CategoryUseCase categoryUseCase) {
        this.categoryUseCase = categoryUseCase;
    }

    @Operation(summary = "Create a new category", description = "Creates a new product category with the provided information")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Category created successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Category code already exists")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @Valid @RequestBody @Parameter(description = "Category data to create") CategoryRequest request) {
        Category category = mapToCategory(request);
        Category createdCategory = categoryUseCase.createCategory(category);
        CategoryResponse response = mapToResponse(createdCategory);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(response));
    }

    @Operation(summary = "Get category by ID", description = "Returns a category based on the provided ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(
            @Parameter(description = "Category ID", required = true) @PathVariable Long id) {
        Category category = categoryUseCase.getCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        CategoryResponse response = mapToResponse(category);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Get all categories", description = "Returns a list of all categories")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of categories retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        List<CategoryResponse> categories = categoryUseCase.getAllCategories().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(categories));
    }

    @Operation(summary = "Get active categories", description = "Returns a list of all active categories")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of active categories retrieved successfully")
    })
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getActiveCategories() {
        List<CategoryResponse> categories = categoryUseCase.getActiveCategories().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(categories));
    }

    @Operation(summary = "Get category by code", description = "Returns a category based on the provided code")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryByCode(
            @Parameter(description = "Category code", required = true) @PathVariable String code) {
        Category category = categoryUseCase.getCategoryByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "code", code));
        CategoryResponse response = mapToResponse(category);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Update category", description = "Updates an existing category with the provided information")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @Parameter(description = "Category ID", required = true) @PathVariable Long id,
            @Valid @RequestBody @Parameter(description = "Updated category data") CategoryRequest request) {
        Category category = mapToCategory(request);
        Category updatedCategory = categoryUseCase.updateCategory(id, category);
        CategoryResponse response = mapToResponse(updatedCategory);
        return ResponseEntity.ok(ApiResponse.success("Category updated successfully", response));
    }

    @Operation(summary = "Delete category", description = "Deletes a category based on the provided ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            @Parameter(description = "Category ID", required = true) @PathVariable Long id) {
        categoryUseCase.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.success("Category deleted successfully", null));
    }

    /**
     * Maps a CategoryRequest DTO to a domain Category entity.
     *
     * @param request the request DTO
     * @return the domain Category entity
     */
    private Category mapToCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setCode(request.getCode());
        category.setActive(request.getActive());
        return category;
    }

    /**
     * Maps a domain Category entity to a CategoryResponse DTO.
     *
     * @param category the domain Category entity
     * @return the response DTO
     */
    private CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getCode(),
                category.getActive(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
