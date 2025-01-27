package org.yam.springbootorderproduct.dtoRequest;

import lombok.Data;

@Data
public class OrderDtoRequest {

    private String priceTotal;
    private String qteTotal;
}