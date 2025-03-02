package org.yam.springbootorderproduct.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yam.springbootorderproduct.dto.dtoRequest.ProductDtoRequest;
import org.yam.springbootorderproduct.dto.dtoResponse.ProductDtoResponse;
import org.yam.springbootorderproduct.model.Product;
import org.yam.springbootorderproduct.service.ProductService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
@RestController
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ProductDtoResponse> createProduct(@Valid @RequestBody ProductDtoRequest body) {

        Product newProduct = modelMapper.map(body, Product.class);
        Product savedProduct = productService.createProduct(newProduct);
        ProductDtoResponse response = modelMapper.map(savedProduct, ProductDtoResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/paggination")
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Product> Products = productService.getProducts(page, size);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Products.getContent());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDtoRequest body, @PathVariable("id") Long id) {

        Product existingProduct = modelMapper.map(body, Product.class);
        existingProduct.setId(id); // Ensure the id remains unchanged
        Product updatedProduct = productService.updateProduct(existingProduct, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }
}
