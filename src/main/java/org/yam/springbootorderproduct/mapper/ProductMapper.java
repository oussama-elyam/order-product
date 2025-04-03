package org.yam.springbootorderproduct.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.yam.springbootorderproduct.dto.ProductDto;
import org.yam.springbootorderproduct.model.Product;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Product toEntity(ProductDto dto) {
        return modelMapper.map(dto, Product.class);
    }

    public ProductDto toRequestDto(Product entity) {
        return modelMapper.map(entity, ProductDto.class);
    }

    public ProductDto toResponseDto(Product entity) {
        return modelMapper.map(entity, ProductDto.class);
    }

}

