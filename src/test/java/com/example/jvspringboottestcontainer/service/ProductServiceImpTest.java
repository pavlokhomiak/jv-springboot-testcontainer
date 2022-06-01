package com.example.jvspringboottestcontainer.service;

import com.example.jvspringboottestcontainer.dto.ProductDtoResponse;
import com.example.jvspringboottestcontainer.model.Product;
import com.example.jvspringboottestcontainer.repository.ProductRepository;
import com.example.jvspringboottestcontainer.util.PageRequestUtil;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProductServiceImpTest {
    @Autowired
    private ProductServiceImp service;
    @MockBean
    private ProductRepository repository;

    @Test
    void shouldReturnValidResponse() {
        List<Product> mockProducts = List.of(
            new Product()
                .setId(1L)
                .setPrice(BigDecimal.valueOf(1249))
                .setTitle("Samsung S10 blue"),
            new Product()
                .setId(13L)
                .setPrice(BigDecimal.valueOf(1378))
                .setTitle("Nokia N1 128 GB"),
            new Product()
                .setId(11L)
                .setPrice(BigDecimal.valueOf(1445))
                .setTitle("Nokia N1 gold"));
        BigDecimal from = BigDecimal.valueOf(1000);
        BigDecimal to = BigDecimal.valueOf(2000);
        PageRequest pageRequest = PageRequestUtil.getPageRequest(3, 0, "price:ASC");
        Mockito
            .when(repository.findAllByPriceBetweenSortedByPriceOrTitle(from, to, pageRequest))
            .thenReturn(mockProducts);
        List<ProductDtoResponse> expected = List.of(
            new ProductDtoResponse()
                .setPrice(BigDecimal.valueOf(1249))
                .setTitle("Samsung S10 blue"),
            new ProductDtoResponse()
                .setPrice(BigDecimal.valueOf(1378))
                .setTitle("Nokia N1 128 GB"),
            new ProductDtoResponse()
                .setPrice(BigDecimal.valueOf(1445))
                .setTitle("Nokia N1 gold"));
        List<ProductDtoResponse> actual = service
            .findAllByPriceBetweenSortedByPriceOrTitle(from, to, pageRequest);
        Mockito.verify(repository, Mockito
            .times(1))
            .findAllByPriceBetweenSortedByPriceOrTitle(from, to, pageRequest);
        Assertions.assertEquals(expected, actual);
    }
}