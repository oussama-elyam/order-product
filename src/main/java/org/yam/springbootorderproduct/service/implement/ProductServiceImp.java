package org.yam.springbootorderproduct.service.implement;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.yam.springbootorderproduct.exception.ProductNameAlreadyExistsException;
import org.yam.springbootorderproduct.model.Product;
import org.yam.springbootorderproduct.model.StatusProduct;
import org.yam.springbootorderproduct.repository.ProductRepository;
import org.yam.springbootorderproduct.service.ProductService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Product createProduct(Product body) {
        if (productRepository.existsByName(body.getName())) {
            throw new ProductNameAlreadyExistsException("Product name must be unique. The name '" + body.getName() + "' already exists.");
        }

        // Set the status to 'AVAILABLE' if the body.status is null
        if (body.getStatusProduct() == null && body.getQte() > 0) {
            body.setStatusProduct(StatusProduct.AVAILABLE);
        } else {
            body.setStatusProduct(StatusProduct.NOTAVAILABLE);
        }
        return productRepository.save(body);
    }
    @Override
    public Page<Product> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    public Product updateProduct(Product body, Long id) {
        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct == null) {
            return null;
        }

        modelMapper.map(body, existingProduct); // This will automatically set all fields
        return productRepository.save(existingProduct);
    }


    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);

    }
}
