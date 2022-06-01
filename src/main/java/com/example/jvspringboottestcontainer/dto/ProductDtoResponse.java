package com.example.jvspringboottestcontainer.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductDtoResponse {
    private String title;
    private BigDecimal price;
}
