package com.example.jvspringboottestcontainer.repository;

import com.example.jvspringboottestcontainer.model.Product;
import com.example.jvspringboottestcontainer.util.PageRequestUtil;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    @Container
    static MySQLContainer<?> database = new MySQLContainer<>("mysql:8")
        .withDatabaseName("product_db")
        .withPassword("springboot")
        .withUsername("springboot");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
    }

    @Autowired
    private ProductRepository repository;

    @Test
    @Sql("/scripts/add_products.sql")
    void shouldReturnProductWithId3() {
        Product actual = repository.getById(3L);
        Assertions.assertEquals(3L, actual.getId());
        Assertions.assertEquals("Samsung S10 yellow", actual.getTitle());
        Assertions.assertEquals(BigDecimal.valueOf(1750), actual.getPrice());
    }

    @Test
    @Sql("/scripts/add_products.sql")
    void shouldDeleteProductWithId3() {
        repository.deleteById(3L);
        Product actual = repository.getById(3L);
        Assertions.assertNull(actual);
    }

    @Test
    @Sql("/scripts/add_products.sql")
    void shouldReturnSortedByPriceDesc() {
        PageRequest pageRequestDesc = PageRequestUtil.getPageRequest(5, 0, "price:DESC");
        List<Product> actualDesc = repository.findAllSortedByPriceOrTitle(pageRequestDesc);
        Assertions.assertEquals(5, actualDesc.size());
        Assertions.assertEquals(BigDecimal.valueOf(2969), actualDesc.get(0).getPrice());
    }

    @Test
    @Sql("/scripts/add_products.sql")
    void shouldReturnSortedByPriceAsc() {
        PageRequest pageRequestAsc = PageRequestUtil.getPageRequest(6, 0, "price:ASC");
        List<Product> actualAsc = repository.findAllSortedByPriceOrTitle(pageRequestAsc);
        Assertions.assertEquals(6, actualAsc.size());
        Assertions.assertEquals(BigDecimal.valueOf(745), actualAsc.get(0).getPrice());
    }

    @Test
    @Sql("/scripts/add_products.sql")
    void shouldReturnSortedByPriceAndTitle() {
        PageRequest pageRequestDesc = PageRequestUtil.getPageRequest(5, 0, "price:DESC;title:ASC");
        List<Product> actualDesc = repository.findAllSortedByPriceOrTitle(pageRequestDesc);
        Assertions.assertEquals(5, actualDesc.size());
        Assertions.assertEquals(BigDecimal.valueOf(2969), actualDesc.get(0).getPrice());
    }

    @Test
    @Sql("/scripts/add_products.sql")
    void shouldThrowQueryException() {
        PageRequest pageRequest = PageRequestUtil.getPageRequest(5, 0, "pric:DESC");
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class,
            () -> repository.findAllSortedByPriceOrTitle(pageRequest));
    }

    @Test
    @Sql("/scripts/add_products.sql")
    void shouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> PageRequestUtil.getPageRequest(5, 0, "price:DES"));
    }

    @Test
    @Sql("/scripts/add_products.sql")
    void shouldReturnByPriceBetweenSortedByPriceOrTitle() {
        BigDecimal from = BigDecimal.valueOf(1000);
        BigDecimal to = BigDecimal.valueOf(2000);
        PageRequest pageRequest = PageRequestUtil.getPageRequest(5, 0, "price:ASC");
        List<Product> actual = repository
                .findAllByPriceBetweenSortedByPriceOrTitle(from, to, pageRequest);
        Assertions.assertEquals(5, actual.size());
        Assertions.assertEquals(BigDecimal.valueOf(1249), actual.get(0).getPrice());
        Assertions.assertEquals("Samsung S10 blue", actual.get(0).getTitle());
    }
}
