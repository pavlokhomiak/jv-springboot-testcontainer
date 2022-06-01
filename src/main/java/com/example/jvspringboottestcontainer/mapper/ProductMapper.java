package com.example.jvspringboottestcontainer.mapper;

import com.example.jvspringboottestcontainer.dto.ProductDtoRequest;
import com.example.jvspringboottestcontainer.dto.ProductDtoResponse;
import com.example.jvspringboottestcontainer.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDtoResponse mapToDto(Product product) {
        return new ProductDtoResponse()
            .setTitle(product.getTitle())
            .setPrice(product.getPrice());
    }

    public Product mapToEntity(ProductDtoRequest dto) {
        return new Product()
            .setTitle(dto.getTitle())
            .setPrice(dto.getPrice());
    }
}
