package org.yam.springbootorderproduct.service;


import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.yam.springbootorderproduct.dto.ProductDto;
import org.yam.springbootorderproduct.exception.ResourceConflictException;
import org.yam.springbootorderproduct.mapper.ProductMapper;
import org.yam.springbootorderproduct.model.Product;
import org.yam.springbootorderproduct.model.StatusProduct;
import org.yam.springbootorderproduct.repository.ProductRepository;
import org.yam.springbootorderproduct.service.impl.ProductServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductDto productDto;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initializes mocks

        productDto = new ProductDto();
        productDto.setName("Test Product");
        productDto.setPrice("100");
        productDto.setQte(50L);
        productDto.setStatusProduct(StatusProduct.AVAILABLE);

        product = new Product();
        product.setName("Test Product");
        product.setPrice("100");
        product.setQte(50L);
        product.setStatusProduct(StatusProduct.AVAILABLE);
    }

    // ✅ 1. Test for createProduct (valid)
    @Test
    void testCreateProduct_Success() {
        when(productMapper.toEntity(any(ProductDto.class))).thenReturn(product);
        when(productRepository.existsByName(anyString())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toResponseDto(any(Product.class))).thenReturn(productDto);

        ProductDto result = productService.createProduct(productDto);

        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    // ✅ 2. Test for createProduct (duplicate name - ResourceConflictException)
    @Test
    void testCreateProduct_DuplicateName() {
        when(productMapper.toEntity(any(ProductDto.class))).thenReturn(product);
        when(productRepository.existsByName(anyString())).thenReturn(true);  // Simulating a duplicate name

        assertThrows(ResourceConflictException.class, () -> productService.createProduct(productDto));
        verify(productRepository, never()).save(any(Product.class));  // Ensure save was not called
    }

    // ✅ 3. Test for getProducts (pagination)
    @Test
    void testGetProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = mock(Page.class);
        when(productRepository.findAll(pageable)).thenReturn(productPage);
        when(productPage.map(any())).thenReturn(mock(Page.class));

        Page<ProductDto> result = productService.getProducts(0, 10);

        assertNotNull(result);
        verify(productRepository, times(1)).findAll(pageable);
    }

    // ✅ 4. Test for updateProduct (valid)
    @Test
    void testUpdateProduct_Success() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productMapper.toEntity(any(ProductDto.class))).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toResponseDto(any(Product.class))).thenReturn(productDto);

        ProductDto result = productService.updateProduct(productDto, 1L);

        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

//    // ✅ 5. Test for updateProduct (EntityNotFoundException)
//    @Test
//    void testUpdateProduct_ProductNotFound() {
//        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class, () -> productService.updateProduct(productDto, 1L));
//        verify(productRepository, never()).save(any(Product.class));  // Ensure save was not called
//    }

    // ✅ 6. Test for deleteProduct (valid)
    @Test
    void testDeleteProduct_Success() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    // ✅ 7. Test for deleteProduct (EntityNotFoundException)
    @Test
    void testDeleteProduct_ProductNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.deleteProduct(1L));
        verify(productRepository, never()).deleteById(anyLong());
    }
}