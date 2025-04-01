package org.yam.springbootorderproduct.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.yam.springbootorderproduct.dto.dtoRequest.ProductDtoRequest;
import org.yam.springbootorderproduct.dto.dtoResponse.ProductDtoResponse;
import org.yam.springbootorderproduct.model.Product;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Product toEntity(ProductDtoRequest dto) {
        return modelMapper.map(dto, Product.class);
    }

    public ProductDtoRequest toRequestDto(Product entity) {
        return modelMapper.map(entity, ProductDtoRequest.class);
    }

    public ProductDtoResponse toResponseDto(Product entity) {
        return modelMapper.map(entity, ProductDtoResponse.class);
    }

}

