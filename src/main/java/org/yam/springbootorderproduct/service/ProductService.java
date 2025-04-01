package org.yam.springbootorderproduct.service;

import org.springframework.data.domain.Page;
import org.yam.springbootorderproduct.dto.dtoRequest.ProductDtoRequest;
import org.yam.springbootorderproduct.dto.dtoResponse.ProductDtoResponse;
import org.yam.springbootorderproduct.model.Product;

public interface ProductService {
    ProductDtoResponse createProduct(ProductDtoRequest body);
    Page<ProductDtoResponse> getProducts(int page, int size);
    ProductDtoResponse updateProduct(ProductDtoRequest body, Long id);
    void deleteProduct(Long id);
}
