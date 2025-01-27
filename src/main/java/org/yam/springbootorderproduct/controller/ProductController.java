package org.yam.springbootorderproduct.controller;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.yam.springbootorderproduct.dtoRequest.ProductDtoRequest;
import org.yam.springbootorderproduct.model.Product;
import org.yam.springbootorderproduct.service.ProductService;


import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/v1/product")
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createProduct(@RequestBody ProductDtoRequest body, BindingResult result) {

        try {
            Product newProduct = new Product();

            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(body, newProduct);

            Product newProductCreated = productService.createProduct(newProduct);

            return new ResponseEntity<>(newProductCreated, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the Product: " + e.getMessage());
        }

    }

    @GetMapping("/getPaggination")
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Product> Products = productService.getProducts(page, size);

        return new ResponseEntity<>(Products.getContent(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateProductSql(@RequestBody ProductDtoRequest body, @PathVariable("id") Long id) {

        Product existingProduct = new Product();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(body, existingProduct);

        existingProduct.setId(id); // Ensure the id remains unchanged

        Product updatedProduct = productService.updateProduct(existingProduct, id);

        if (updatedProduct == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

}
