package org.yam.springbootorderproduct.dto.dtoRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.yam.springbootorderproduct.model.StatusProduct;

@Data
public class ProductDtoRequest {
    @NotBlank(message = "Name shouldn't be NULL or EMPTY")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters long")
    private String name;
    @NotBlank(message = "price shouldn't be NULL or EMPTY")
    private String price;
    @NotBlank(message = "QTE shouldn't be NULL or EMPTY")
    private String qte;
}
