package com.example.jvspringboottestcontainer.controller;

import com.example.jvspringboottestcontainer.dto.ProductDtoRequest;
import com.example.jvspringboottestcontainer.dto.ProductDtoResponse;
import com.example.jvspringboottestcontainer.model.Product;
import com.example.jvspringboottestcontainer.service.ProductService;
import com.example.jvspringboottestcontainer.util.PageRequestUtil;
import java.math.BigDecimal;
import java.util.List;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProductControllerTest {
    @MockBean
    private ProductService service;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @BeforeEach
    void setUpBeforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

//    @Test
//    void shouldCreateProduct() {
//        Product mockProduct = new Product()
//            .setPrice(BigDecimal.valueOf(1000))
//            .setTitle("Iphone 10");
//        Product mockReturnedProduct = new Product()
//            .setId(1L)
//            .setPrice(BigDecimal.valueOf(1000))
//            .setTitle("Iphone 10");
//        Mockito
//            .when(service.save(mockProduct))
//            .thenReturn(mockReturnedProduct);
//
//        ProductDtoRequest request = new ProductDtoRequest()
//            .setPrice(mockProduct.getPrice())
//            .setTitle(mockProduct.getTitle());
//        RestAssuredMockMvc
//            .given()
//                .contentType(ContentType.JSON)
//                .body(request)
//            .when()
//                .post("/products")
//            .then()
//                .statusCode(200)
//                .body("price", Matchers.equalTo(1000))
//                .body("title", Matchers.equalTo("Iphone 10"));
//    }

    @Test
    void shouldReturnProductWithId2() {
        Product mockProduct = new Product()
            .setId(2L)
            .setPrice(BigDecimal.valueOf(1000))
            .setTitle("Iphone 10");
        Mockito
            .when(service.getById(2L))
            .thenReturn(mockProduct);

        RestAssuredMockMvc
            .when()
                .get("/products/2")
            .then()
                .statusCode(200)
                .body("price", Matchers.equalTo(1000))
                .body("title", Matchers.equalTo("Iphone 10"));
    }

    @Test
    void update() {
    }

    @Test
    void findAllSortedByPriceOrTitle() {
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
        PageRequest pageRequest = PageRequestUtil.getPageRequest(3, 0, "price:ASC");
        Mockito
            .when(service.findAllSortedByPriceOrTitle(pageRequest))
            .thenReturn(mockProducts);
        RestAssuredMockMvc
            .given()
                .param("count", 3)
                .param("page", 0)
                .param("sortBy", "price:ASC")
            .when()
                .get("/products")
            .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(3))
                .body("[0].title", Matchers.equalTo("Samsung S10 blue"))
                .body("[0].price", Matchers.equalTo(1249));
    }

    @Test
    void findAllBetweenPriceSortedByPriceOrTitle() {
        List<ProductDtoResponse> mockProducts = List.of(
            new ProductDtoResponse()
                .setPrice(BigDecimal.valueOf(1249))
                .setTitle("Samsung S10 blue"),
            new ProductDtoResponse()
                .setPrice(BigDecimal.valueOf(1378))
                .setTitle("Nokia N1 128 GB"),
            new ProductDtoResponse()
                .setPrice(BigDecimal.valueOf(1445))
                .setTitle("Nokia N1 gold"));
        BigDecimal from = BigDecimal.valueOf(1000);
        BigDecimal to = BigDecimal.valueOf(2000);
        PageRequest pageRequest = PageRequestUtil.getPageRequest(3, 0, "price:ASC");
        Mockito
            .when(service.findAllByPriceBetweenSortedByPriceOrTitle(from, to, pageRequest))
            .thenReturn(mockProducts);
        RestAssuredMockMvc
            .given()
                .param("from", 1000)
                .param("to", 2000)
                .param("count", 3)
                .param("page", 0)
                .param("sortBy", "price:ASC")
            .when()
                .get("/products/price/between")
            .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(3))
                .body("[0].title", Matchers.equalTo("Samsung S10 blue"))
                .body("[0].price", Matchers.equalTo(1249));
    }
}