package org.yam.springbootorderproduct.service;

import org.springframework.data.domain.Page;
import org.yam.springbootorderproduct.model.Product;

import java.util.Optional;

public interface ProductService {
    Product createProduct(Product body);
    Page<Product> getProducts(int page, int size);
    Product updateProduct(Product body, Long id);
    void deleteProduct(Long id);
    Optional<Product> getProductById(Long id);

}
