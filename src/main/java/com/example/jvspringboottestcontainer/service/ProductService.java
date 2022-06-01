package com.example.jvspringboottestcontainer.service;

import com.example.jvspringboottestcontainer.dto.ProductDtoResponse;
import com.example.jvspringboottestcontainer.model.Product;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product save(Product product);

    Product getById(Long id);

    void deleteById(Long id);

    List<Product> findAllSortedByPriceOrTitle(PageRequest pageRequest);

    List<ProductDtoResponse> findAllByPriceBetweenSortedByPriceOrTitle(
            BigDecimal from, BigDecimal to, PageRequest pageRequest);
}
