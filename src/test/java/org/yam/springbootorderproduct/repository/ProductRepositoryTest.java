//package org.yam.springbootorderproduct.repository;
//
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.yam.springbootorderproduct.model.Product;
//import org.yam.springbootorderproduct.model.StatusProduct;
//
//import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
//import static org.hibernate.validator.internal.util.Contracts.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//@DataJpaTest
//public class ProductRepositoryTest {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    private Product product;
//
//    @BeforeEach
//    void setUp() {
//        // Initialize test data
//        product = new Product();
//        product.setName("Test Product");
//        product.setPrice("100");
//        product.setQte(50l);
//        product.setStatusProduct(StatusProduct.AVAILABLE);
//
//        // Save product to the database
//        productRepository.save(product);
//    }
//
//    // ✅ Test for checking if a product exists by name
//    @Test
//    void testExistsByName() {
//        boolean exists = productRepository.existsByName("Test Product");
//
//        assertTrue(exists, "Product should exist by name");
//    }
//
//    // ✅ Test for checking if a product does not exist by a name
//    @Test
//    void testDoesNotExistByName() {
//        boolean exists = productRepository.existsByName("Nonexistent Product");
//
//        assertFalse(exists, "Product should not exist by name");
//    }
//
//    // ✅ Test for saving and retrieving a product
//    @Test
//    void testSaveAndFindProduct() {
//        Product foundProduct = productRepository.findById(product.getId()).orElse(null);
//
//        assertNotNull(foundProduct, "Product should be found by ID");
//        assertEquals("Test Product", foundProduct.getName(), "Product name should match");
//        assertEquals("100", foundProduct.getPrice(), "Product price should match");
//        assertEquals(50L, foundProduct.getQte(), "Product quantity should match");
//        assertEquals(StatusProduct.AVAILABLE, foundProduct.getStatusProduct(), "Product status should match");
//    }
//
//    // ✅ Test for deleting a product
//    @Test
//    void testDeleteProduct() {
//        long productId = product.getId();
//        productRepository.deleteById(productId);
//
//        boolean exists = productRepository.existsById(productId);
//        assertFalse(exists, "Product should be deleted");
//    }
//}