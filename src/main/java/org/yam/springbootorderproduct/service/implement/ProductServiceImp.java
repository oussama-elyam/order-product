package org.yam.springbootorderproduct.service.implement;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.yam.springbootorderproduct.model.Product;
import org.yam.springbootorderproduct.repository.ProductRepository;
import org.yam.springbootorderproduct.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Product body) {
        return productRepository.save(body);
    }
    @Override
    public Page<Product> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    public Product updateProduct(Product body, Long id) {
        return null;
    }


    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
