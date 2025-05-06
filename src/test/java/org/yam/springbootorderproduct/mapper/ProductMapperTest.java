//package org.yam.springbootorderproduct.mapper;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.yam.springbootorderproduct.dto.ProductDto;
//import org.yam.springbootorderproduct.model.Product;
//import org.yam.springbootorderproduct.model.StatusProduct;
//
//import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class ProductMapperTest {
//
//    private ProductMapper productMapper;
//
//    private ProductDto productDto;
//    private Product product;
//
//    @BeforeEach
//    void setUp() {
//        // Initialize ModelMapper and ProductMapper
//        ModelMapper modelMapper = new ModelMapper();
//        productMapper = new ProductMapper(modelMapper);
//
//        // Set up test data
//        productDto = new ProductDto();
//        productDto.setName("Test Product");
//        productDto.setPrice("100");
//        productDto.setQte(50L);
//        productDto.setStatusProduct(StatusProduct.AVAILABLE);
//
//        product = new Product();
//        product.setName("Test Product");
//        product.setPrice("100");
//        product.setQte(50L);
//        product.setStatusProduct(StatusProduct.AVAILABLE);
//    }
//
//    // ✅ Test for mapping ProductDto to Product (toEntity method)
//    @Test
//    void testToEntity() {
//        Product entity = productMapper.toEntity(productDto);
//
//        assertNotNull(entity);
//        assertEquals(productDto.getName(), entity.getName());
//        assertEquals(productDto.getPrice(), entity.getPrice());
//        assertEquals(productDto.getQte(), entity.getQte());
//        assertEquals(productDto.getStatusProduct(), entity.getStatusProduct());
//    }
//
//    // ✅ Test for mapping Product to ProductDto (toRequestDto method)
//    @Test
//    void testToRequestDto() {
//        ProductDto dto = productMapper.toRequestDto(product);
//
//        assertNotNull(dto);
//        assertEquals(product.getName(), dto.getName());
//        assertEquals(product.getPrice(), dto.getPrice());
//        assertEquals(product.getQte(), dto.getQte());
//        assertEquals(product.getStatusProduct(), dto.getStatusProduct());
//    }
//
//    // ✅ Test for mapping Product to ProductDto (toResponseDto method)
//    @Test
//    void testToResponseDto() {
//        ProductDto dto = productMapper.toResponseDto(product);
//
//        assertNotNull(dto);
//        assertEquals(product.getName(), dto.getName());
//        assertEquals(product.getPrice(), dto.getPrice());
//        assertEquals(product.getQte(), dto.getQte());
//        assertEquals(product.getStatusProduct(), dto.getStatusProduct());
//    }
//}
