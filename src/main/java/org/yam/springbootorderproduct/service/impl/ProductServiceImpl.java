package org.yam.springbootorderproduct.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.yam.springbootorderproduct.exception.ResourceConflictException;
import org.yam.springbootorderproduct.model.Product;
import org.yam.springbootorderproduct.model.StatusProduct;
import org.yam.springbootorderproduct.repository.ProductRepository;
import org.yam.springbootorderproduct.service.ProductService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Transactional
    @Override
    public Product createProduct(Product body) {
        if (productRepository.existsByName(body.getName())) {
            throw new ResourceConflictException("Product name must be unique. The name '" + body.getName() + "' already exists.");
        }

        Product newProduct = Product.builder()
                .name(body.getName())
                .price(body.getPrice())
                .qte(body.getQte())
                .statusProduct((body.getStatusProduct() == null && body.getQte() > 0)
                        ? StatusProduct.AVAILABLE
                        : StatusProduct.NOTAVAILABLE)
                .build();

        return productRepository.save(newProduct);
    }

    @Override
    public Page<Product> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Product updateProduct(Product body, Long id) {

        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            logger.warn("Product with id {} not found", id);
            throw new EntityNotFoundException("Product with ID " + id + " not found");
        }

        body.setStatusProduct(body.getQte() > 0 ? StatusProduct.AVAILABLE : StatusProduct.NOTAVAILABLE);
        modelMapper.map(body, existingProduct);

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {

        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()) {
            logger.warn("Product with id {} not found", id);
            throw new EntityNotFoundException("Product with ID " + id + " not found");
        }
        productRepository.deleteById(id);
    }
}
