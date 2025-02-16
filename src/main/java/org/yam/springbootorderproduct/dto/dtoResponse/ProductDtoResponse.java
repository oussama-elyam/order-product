package org.yam.springbootorderproduct.dto.dtoResponse;

import lombok.Data;
import org.yam.springbootorderproduct.model.StatusProduct;

@Data
public class ProductDtoResponse {
    private Long id;
    private String name;
    private double price;
    private Enum<StatusProduct> statusProduct;

}