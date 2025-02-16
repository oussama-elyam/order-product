package org.yam.springbootorderproduct.dto.dtoRequest;

import lombok.Data;

@Data
public class OrderDtoRequest {

    private String priceTotal;
    private String qteTotal;
}