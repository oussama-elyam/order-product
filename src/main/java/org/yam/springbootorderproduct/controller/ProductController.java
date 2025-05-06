package org.yam.springbootorderproduct.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yam.springbootorderproduct.dto.ProductDto;
import org.yam.springbootorderproduct.service.ProductService;
import org.yam.springbootorderproduct.views.ProductViews;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @JsonView(ProductViews.Response.class)  // Returns only Public fields
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto body) {

        ProductDto savedProduct = productService.createProduct(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/paggination")
    public ResponseEntity<List<ProductDto>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ProductDto> Products = productService.getProducts(page, size);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Products.getContent());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto body, @PathVariable("id") Long id) {

        ProductDto updatedProduct = productService.updateProduct(body, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }
}
