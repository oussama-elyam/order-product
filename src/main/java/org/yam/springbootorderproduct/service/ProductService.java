package org.yam.springbootorderproduct.service;

import org.springframework.data.domain.Page;
import org.yam.springbootorderproduct.dto.ProductDto;

public interface ProductService {
    ProductDto createProduct(ProductDto body);
    Page<ProductDto> getProducts(int page, int size);
    ProductDto updateProduct(ProductDto body, Long id);
    void deleteProduct(Long id);
}
