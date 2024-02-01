package com.dostavka24.dostavka24.rests.orders;

import com.dostavka24.dostavka24.domain.dtos.orders.orderitem.OrderItemCreationDto;
import com.dostavka24.dostavka24.domain.entities.restaurants.Product;
import com.dostavka24.dostavka24.service.utils.SecurityUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderItemControllerIntegrationTest {

    @Autowired
    WebApplicationContext wac;

    WebTestClient client;

    // Assuming OrderItemCreationDto is correctly implemented
    private OrderItemCreationDto orderItemCreationDto;

    @BeforeEach
    void setUp() {
        client = MockMvcWebTestClient.bindToApplicationContext(this.wac)
                .configureClient()
                .baseUrl("http://localhost:8081")
                .build();

        // Setup product data here
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(100);
        // Save product to the repository

        orderItemCreationDto = new OrderItemCreationDto();
        orderItemCreationDto.setName("Test Product");
        orderItemCreationDto.setCount(1);

    }

    @Test
    @Order(1)
    @WithMockUser(username = "azimochilov", authorities = "USER_CREATE_ORDER_ITEM")
    void createOrderItem_ReturnStatusOk() {
        try (MockedStatic<SecurityUtils> utilities = Mockito.mockStatic(SecurityUtils.class)) {
            utilities.when(SecurityUtils::getCurrentUserId).thenReturn(13L);
            client.post().uri("/order/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(orderItemCreationDto), OrderItemCreationDto.class)
                    .exchange()
                    .expectStatus().isOk();
        }
    }

}
