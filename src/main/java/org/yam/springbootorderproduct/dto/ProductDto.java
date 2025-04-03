package org.yam.springbootorderproduct.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.yam.springbootorderproduct.model.StatusProduct;
import org.yam.springbootorderproduct.views.ProductViews;

@Data
public class ProductDto {
    @JsonView(ProductViews.Response.class)
    private Long id;

    @NotBlank(message = "Name shouldn't be NULL or EMPTY")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters long")
    @JsonView({ProductViews.Request.class, ProductViews.Response.class})
    private String name;

    @NotBlank(message = "price shouldn't be NULL or EMPTY")
    @JsonView({ProductViews.Request.class, ProductViews.Response.class})
    private String price;

    @NotNull(message = "QTE shouldn't be NULL")
    @JsonView({ProductViews.Request.class, ProductViews.Response.class})
    private Long qte;

    @JsonView(ProductViews.Response.class) // Status is only for response
    @Enumerated(EnumType.STRING)
    private StatusProduct statusProduct;
}
