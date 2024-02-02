package com.dostavka24.dostavka24.rests.restaurants;

import com.dostavka24.dostavka24.domain.dtos.products.ProductCreationDto;
import com.dostavka24.dostavka24.domain.dtos.products.ProductUpdateDto;
import com.dostavka24.dostavka24.domain.enums.ProductStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Mono;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerIntegrationTest {

    @Autowired
    WebApplicationContext wac;

    WebTestClient client;

    private ProductCreationDto productCreationDto;
    private ProductUpdateDto productUpdateDto;

    @BeforeEach
    void setUp() {
        client = MockMvcWebTestClient.bindToApplicationContext(this.wac)
                .configureClient()
                .baseUrl("http://localhost:8081")
                .build();

        productCreationDto = new ProductCreationDto();
        productCreationDto.setName("TEsasdasdasdt");
        productCreationDto.setPrice(100);
        productCreationDto.setProductStatus(ProductStatus.CHICKEN);

        productUpdateDto = new ProductUpdateDto();
        productUpdateDto.setName("Test Pasdroduct");
        productUpdateDto.setPrice(101);
        productUpdateDto.setProductStatus(ProductStatus.BURGER);
    }

    @Test
    @Order(1)
    @WithMockUser(username = "admin", authorities = "CREATE")
    void createProduct_ReturnStatusOk() {
        client.post().uri("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(productCreationDto), ProductCreationDto.class)
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", authorities = "DELETE")
    void deleteProduct_ReturnStatusOk() {
        client.delete().uri("/product/1")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin")
    void getAllProducts_ReturnStatusOk() {
        client.get().uri("/product")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(List.class);
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin")
    void getProductById_ReturnStatusOk() {
        client.get().uri("/product/2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ProductCreationDto.class)
                .returnResult();;
    }

    @Test
    @Order(5)
    @WithMockUser(username = "admin", authorities = "UPDATE")
    void updateProduct_ReturnStatusOk() {
        client.put().uri("/product/2")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(productUpdateDto), ProductUpdateDto.class)
                .exchange()
                .expectStatus().isOk();
    }
}

