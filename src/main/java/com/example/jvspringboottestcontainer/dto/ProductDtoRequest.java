package com.example.jvspringboottestcontainer.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductDtoRequest {
    @NotBlank(message = "Tittle cannot be blank")
    private String title;
    @NotEmpty(message = "Price cannot be empty")
    private BigDecimal price;
}
