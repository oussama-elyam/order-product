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


        //we can use moddel mapper and not using builder because the object is not that complexe to build
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
    public Product updateProduct(Product body, Long id) {
        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct == null) {
            return null;
        }
        body.setStatusProduct(body.getQte() > 0 ? StatusProduct.AVAILABLE : StatusProduct.NOTAVAILABLE);
        modelMapper.map(body, existingProduct);

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
