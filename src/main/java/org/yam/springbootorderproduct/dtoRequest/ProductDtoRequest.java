package org.yam.springbootorderproduct.dtoRequest;

import lombok.Data;
import org.yam.springbootorderproduct.model.StatusProduct;

@Data
public class ProductDtoRequest {
    private String name;
    private String price;
    private String qte;
    private Enum<StatusProduct> statusProduct;
}
