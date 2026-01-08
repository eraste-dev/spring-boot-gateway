package com.eraste.productservice.infrastructure.adapter.in.web;

import com.eraste.common.exception.ResourceNotFoundException;
import com.eraste.common.response.ApiResponse;
import com.eraste.productservice.domain.model.Category;
import com.eraste.productservice.domain.model.Product;
import com.eraste.productservice.domain.port.in.CategoryUseCase;
import com.eraste.productservice.domain.port.in.ProductUseCase;
import com.eraste.productservice.infrastructure.adapter.in.web.dto.CategoryResponse;
import com.eraste.productservice.infrastructure.adapter.in.web.dto.ProductRequest;
import com.eraste.productservice.infrastructure.adapter.in.web.dto.ProductResponse;
import com.eraste.productservice.infrastructure.adapter.in.web.dto.StockUpdateRequest;
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
 * REST Controller for Product operations.
 * <p>
 * This controller serves as the primary adapter (driving adapter) in the hexagonal architecture.
 * It handles HTTP requests and delegates business logic to the {@link ProductUseCase} port.
 * </p>
 * <p>
 * All endpoints return responses wrapped in {@link ApiResponse} for consistent API responses.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 * @see ProductUseCase
 */
@Tag(name = "Products", description = "Product management API")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductUseCase productUseCase;
    private final CategoryUseCase categoryUseCase;

    /**
     * Constructs a ProductController with the required use cases.
     *
     * @param productUseCase  the use case for product operations
     * @param categoryUseCase the use case for category operations
     */
    public ProductController(ProductUseCase productUseCase, CategoryUseCase categoryUseCase) {
        this.productUseCase = productUseCase;
        this.categoryUseCase = categoryUseCase;
    }

    @Operation(summary = "Create a new product", description = "Creates a new product with the provided information")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "SKU already exists")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @Valid @RequestBody @Parameter(description = "Product data to create") ProductRequest request) {
        Product product = mapToProduct(request);
        Product createdProduct = productUseCase.createProduct(product);
        ProductResponse response = mapToResponse(createdProduct);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(response));
    }

    @Operation(summary = "Get product by ID", description = "Returns a product based on the provided ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(
            @Parameter(description = "Product ID", required = true) @PathVariable Long id) {
        Product product = productUseCase.getProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        ProductResponse response = mapToResponse(product);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Get all products", description = "Returns a list of all products")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of products retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {
        List<ProductResponse> products = productUseCase.getAllProducts().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    @Operation(summary = "Get active products", description = "Returns a list of all active products")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of active products retrieved successfully")
    })
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getActiveProducts() {
        List<ProductResponse> products = productUseCase.getActiveProducts().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    @Operation(summary = "Get products by category", description = "Returns products filtered by category ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of products in category retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByCategoryId(
            @Parameter(description = "Category ID", required = true) @PathVariable Long categoryId) {
        // Verify category exists
        categoryUseCase.getCategoryById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        List<ProductResponse> products = productUseCase.getProductsByCategoryId(categoryId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    @Operation(summary = "Get product by SKU", description = "Returns a product based on the provided SKU")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductBySku(
            @Parameter(description = "Product SKU", required = true) @PathVariable String sku) {
        Product product = productUseCase.getProductBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "sku", sku));
        ProductResponse response = mapToResponse(product);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "Update product", description = "Updates an existing product with the provided information")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @Parameter(description = "Product ID", required = true) @PathVariable Long id,
            @Valid @RequestBody @Parameter(description = "Updated product data") ProductRequest request) {
        Product product = mapToProduct(request);
        Product updatedProduct = productUseCase.updateProduct(id, product);
        ProductResponse response = mapToResponse(updatedProduct);
        return ResponseEntity.ok(ApiResponse.success("Product updated successfully", response));
    }

    @Operation(summary = "Update product stock", description = "Updates the stock quantity of a product")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Stock updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid quantity"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<ProductResponse>> updateStock(
            @Parameter(description = "Product ID", required = true) @PathVariable Long id,
            @Valid @RequestBody @Parameter(description = "Stock update data") StockUpdateRequest request) {
        Product updatedProduct = productUseCase.updateStock(id, request.getQuantity());
        ProductResponse response = mapToResponse(updatedProduct);
        return ResponseEntity.ok(ApiResponse.success("Stock updated successfully", response));
    }

    @Operation(summary = "Delete product", description = "Deletes a product based on the provided ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
            @Parameter(description = "Product ID", required = true) @PathVariable Long id) {
        productUseCase.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success("Product deleted successfully", null));
    }

    /**
     * Maps a ProductRequest DTO to a domain Product entity.
     * <p>
     * Resolves the category by ID if provided.
     * </p>
     *
     * @param request the request DTO
     * @return the domain Product entity
     * @throws ResourceNotFoundException if category ID is provided but not found
     */
    private Product mapToProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setSku(request.getSku());
        product.setActive(request.getActive());

        // Resolve category if categoryId is provided
        if (request.getCategoryId() != null) {
            Category category = categoryUseCase.getCategoryById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));
            product.setCategory(category);
        }

        return product;
    }

    /**
     * Maps a domain Product entity to a ProductResponse DTO.
     * <p>
     * Includes category information if the product has a category.
     * </p>
     *
     * @param product the domain Product entity
     * @return the response DTO
     */
    private ProductResponse mapToResponse(Product product) {
        Category category = product.getCategory();
        CategoryResponse categoryResponse = null;
        if (category != null) {
            categoryResponse = new CategoryResponse(
                    category.getId(),
                    category.getName(),
                    category.getDescription(),
                    category.getCode(),
                    category.getActive(),
                    category.getCreatedAt(),
                    category.getUpdatedAt()
            );
        }
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getSku(),
                category != null ? category.getId() : null,
                categoryResponse,
                product.getActive(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
