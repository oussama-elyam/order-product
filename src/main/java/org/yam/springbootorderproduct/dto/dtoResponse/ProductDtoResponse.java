package org.yam.springbootorderproduct.dto.dtoResponse;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.yam.springbootorderproduct.model.StatusProduct;
import org.yam.springbootorderproduct.views.ProductViews;

@Data
public class ProductDtoResponse {
    @JsonView(ProductViews.Public.class)
    private Long id;
    @JsonView(ProductViews.Public.class)
    private String name;
    @JsonView(ProductViews.Public.class)
    private double price;
    @JsonView(ProductViews.Public.class)
    private Enum<StatusProduct> statusProduct;

}