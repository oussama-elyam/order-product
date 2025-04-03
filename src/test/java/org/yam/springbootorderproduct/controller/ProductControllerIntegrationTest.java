package org.yam.springbootorderproduct.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.yam.springbootorderproduct.dto.ProductDto;
import org.yam.springbootorderproduct.repository.ProductRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    private ProductDto testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new ProductDto();
        testProduct.setName("Test Product");
        testProduct.setPrice("100");
        testProduct.setQte(50L);
    }

    // ✅ 1. Create Product Test
    @Test
    void testCreateProduct_Success() throws Exception {
        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value("100"))
                .andExpect(jsonPath("$.qte").value(50L));
    }

    // ✅ 2. Create Product - Validation Error
    @Test
    void testCreateProduct_ValidationFailure() throws Exception {
        testProduct.setName("");  // Name is blank (violates @NotBlank)
        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProduct)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Validation Error"))  // Check for title in error response
                .andExpect(jsonPath("$.status").value(400))  // Status should be 400
                .andExpect(jsonPath("$.errors").exists())  // Ensure errors array exists
                .andExpect(jsonPath("$.errors[0]").value("name: Name shouldn't be NULL or EMPTY"))
                .andExpect(jsonPath("$.errors[1]").value("name: Name must be between 3 and 100 characters long"));
    }

    // ✅ 3. Get Products with Pagination
    @Test
    void testGetProducts_Pagination() throws Exception {
        // Add a product first
        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProduct)))
                .andExpect(status().isCreated());

        // Retrieve paginated products
        mockMvc.perform(get("/api/v1/product/paggination?page=0&size=10"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Product"));
    }

    // ✅ 4. Update Product
    @Test
    void testUpdateProduct_Success() throws Exception {
        // Create a product first
        String response = mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProduct)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductDto createdProduct = objectMapper.readValue(response, ProductDto.class);

        // Update the product
        createdProduct.setName("Updated Product");
        createdProduct.setPrice("150");

        mockMvc.perform(put("/api/v1/product/" + createdProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.price").value("150"));
    }

    // ✅ 5. Delete Product
    @Test
    void testDeleteProduct_Success() throws Exception {
        // Create a product first
        String response = mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProduct)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductDto createdProduct = objectMapper.readValue(response, ProductDto.class);

        // Delete the product
        mockMvc.perform(delete("/api/v1/product/" + createdProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted successfully"));
    }

    // ✅ 6. Delete Product - Not Found
    @Test
    void testDeleteProduct_NotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/product/999")) // Non-existent ID
                .andExpect(status().isNotFound());
    }
}
