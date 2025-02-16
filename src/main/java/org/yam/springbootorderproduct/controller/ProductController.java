package org.yam.springbootorderproduct.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.yam.springbootorderproduct.dto.dtoRequest.ProductDtoRequest;
import org.yam.springbootorderproduct.dto.dtoResponse.ProductDtoResponse;
import org.yam.springbootorderproduct.exception.ProductNameAlreadyExistsException;
import org.yam.springbootorderproduct.model.Product;
import org.yam.springbootorderproduct.service.ProductService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
@RestController
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);



    @PostMapping
    @Transactional
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDtoRequest body, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    result.getFieldErrors().stream()
                            .map(error -> error.getField() + ": " + error.getDefaultMessage())
                            .toList()
            );
        }

        try {
            Product newProduct = modelMapper.map(body, Product.class);

            Product savedProduct = productService.createProduct(newProduct);
            ProductDtoResponse response = modelMapper.map(savedProduct, ProductDtoResponse.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (ProductNameAlreadyExistsException e) {
            logger.error("Product name already exists: {}", e.getMessage());  // Logging the exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the product.");
        }
    }

    @GetMapping("/paggination")
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Product> Products = productService.getProducts(page, size);

        return new ResponseEntity<>(Products.getContent(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Long id) {
        Optional<Product> existingProductOpt = productService.getProductById(id);

        if (existingProductOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductDtoRequest body, @PathVariable("id") Long id) {
        try {

            Product existingProduct = modelMapper.map(body, Product.class);
            existingProduct.setId(id); // Ensure the id remains unchanged

            Product updatedProduct = productService.updateProduct(existingProduct, id);

            if (updatedProduct == null) {
                logger.warn("Product with id {} not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Product not found");
            }

            return ResponseEntity.ok(updatedProduct);

        } catch (Exception e) {
            logger.error("An error occurred while updating the product with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the product.");
        }
    }

}
