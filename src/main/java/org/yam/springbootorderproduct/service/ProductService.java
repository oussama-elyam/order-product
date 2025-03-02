package org.yam.springbootorderproduct.service;

import org.springframework.data.domain.Page;
import org.yam.springbootorderproduct.model.Product;

public interface ProductService {
    Product createProduct(Product body);
    Page<Product> getProducts(int page, int size);
    Product updateProduct(Product body, Long id);
    void deleteProduct(Long id);
}
