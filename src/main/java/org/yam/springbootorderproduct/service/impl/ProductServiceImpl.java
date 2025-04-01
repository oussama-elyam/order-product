package org.yam.springbootorderproduct.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.yam.springbootorderproduct.dto.dtoRequest.ProductDtoRequest;
import org.yam.springbootorderproduct.dto.dtoResponse.ProductDtoResponse;
import org.yam.springbootorderproduct.exception.ResourceConflictException;
import org.yam.springbootorderproduct.mapper.ProductMapper;
import org.yam.springbootorderproduct.model.Product;
import org.yam.springbootorderproduct.model.StatusProduct;
import org.yam.springbootorderproduct.repository.ProductRepository;
import org.yam.springbootorderproduct.service.ProductService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Transactional
    @Override
    public ProductDtoResponse createProduct(ProductDtoRequest bodyRequest) {
        Product body = productMapper.toEntity(bodyRequest);


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

        return productMapper.toResponseDto(productRepository.save(newProduct));
    }

    @Override
    public Page<ProductDtoResponse> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(productMapper::toResponseDto);
    }

    @Override
    @Transactional
    public ProductDtoResponse updateProduct(ProductDtoRequest bodyRequest, Long id) {
        Product body = productMapper.toEntity(bodyRequest);
        body.setId(id); // Ensure the id remains unchanged

        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            logger.warn("Product with id {} not found", id);
            throw new EntityNotFoundException("Product with ID " + id + " not found");
        }

        body.setStatusProduct(body.getQte() > 0 ? StatusProduct.AVAILABLE : StatusProduct.NOTAVAILABLE);

        existingProduct.setName(body.getName());
        existingProduct.setPrice(body.getPrice());
        existingProduct.setQte(body.getQte());
        existingProduct.setStatusProduct(body.getStatusProduct());

        return productMapper.toResponseDto(productRepository.save(body));
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
