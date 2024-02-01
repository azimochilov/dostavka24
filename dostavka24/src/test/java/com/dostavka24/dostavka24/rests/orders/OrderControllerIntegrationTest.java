package com.dostavka24.dostavka24.rests.orders;

import com.dostavka24.dostavka24.domain.dtos.addresses.AddressCreationDto;
import com.dostavka24.dostavka24.domain.dtos.orders.OrderCreationDto;
import com.dostavka24.dostavka24.service.utils.SecurityUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerIntegrationTest {

    @Autowired
    WebApplicationContext wac;

    WebTestClient client;

    private OrderCreationDto orderCreationDto;

    @BeforeEach
    void setUp() {
        client = MockMvcWebTestClient.bindToApplicationContext(this.wac)
                .configureClient()
                .baseUrl("http://localhost:8081")
                .build();

        AddressCreationDto addressCreationDto = new AddressCreationDto();
        orderCreationDto = new OrderCreationDto();
        orderCreationDto.setAddress(addressCreationDto);

    }

    @Test
    @Order(1)
    @WithMockUser(username = "azimochilov", authorities = "CREATE_ORDER_USER")
    void createOrder_ReturnStatusOk() {
        try (MockedStatic<SecurityUtils> utilities = Mockito.mockStatic(SecurityUtils.class)) {
            utilities.when(SecurityUtils::getCurrentUserId).thenReturn(1L);
            client.post().uri("/orders")
                    .body(Mono.just(orderCreationDto), OrderCreationDto.class)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(OrderCreationDto.class)
                    .returnResult();
        }
    }

    @Test
    @Order(2)
    @WithMockUser(username = "waiter", authorities = "CHANGE_STATUS_WAITER")
    void rejectOrder_ReturnStatusOk() {
        client.patch().uri("/orders/status/reject/5")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(3)
    @WithMockUser(username = "waiter", authorities = "CHANGE_STATUS_WAITER")
    void acceptOrder_ReturnStatusOk() {
        client.patch().uri("/orders/status/accept/5")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(4)
    @WithMockUser(username = "waiter", authorities = "CHANGE_STATUS_WAITER")
    void deliverOrder_ReturnStatusOk() {
        client.patch().uri("/orders/status/deliver/5")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(5)
    @WithMockUser(username = "admin", authorities = "ORDER_SERVICE")
    void getAllOrders_ReturnStatusOk() {
        client.get().uri("/orders")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Order(6)
    @WithMockUser(username = "admin", authorities = "CHANGE_STATUS_WAITER")
    void getAllOrdersByStatus_ReturnStatusOk() {
        client.get().uri("/orders/false")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);

    }
}

