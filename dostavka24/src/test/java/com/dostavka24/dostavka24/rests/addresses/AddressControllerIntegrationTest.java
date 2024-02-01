package com.dostavka24.dostavka24.rests.addresses;
import com.dostavka24.dostavka24.domain.dtos.addresses.AddressCreationDto;
import com.dostavka24.dostavka24.domain.dtos.addresses.AddressUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.extension.ExtendWith;
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
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class AddressControllerIntegrationTest {

    @Autowired
    WebApplicationContext wac;

    WebTestClient client;

    private AddressCreationDto addressCreationDto;
    private AddressUpdateDto addressUpdateDto;

    @BeforeEach
    void setUp() {
        client = MockMvcWebTestClient.bindToApplicationContext(this.wac)
                .configureClient()
                .baseUrl("http://localhost:8081")
                .build();

        addressCreationDto = new AddressCreationDto();

        addressUpdateDto = new AddressUpdateDto();

    }

    @Test
    @Order(1)
    @WithMockUser(username = "admin", authorities = "CREATE_USER")
    void createAddress_ReturnStatusOk() {
        client.post().uri("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(addressCreationDto), AddressCreationDto.class)
                .exchange()
                .expectStatus().isOk();
    }

//    @Test
//    @Order(2)
//    @WithMockUser(username = "admin")
//    void deleteAddress_ReturnStatusOk() {
//        client.delete().uri("/address/1")
//                .exchange()
//                .expectStatus().isNoContent();
//    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin")
    void getAllAddresses_ReturnStatusOk() {
        client.get().uri("/address")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(List.class);
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin")
    void getAddressById_ReturnStatusOk() {
        client.get().uri("/address/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(AddressCreationDto.class)
                .returnResult();;
    }

    @Test
    @Order(5)
    @WithMockUser(username = "admin", authorities = "UPDATE_USER")
    void updateAddress_ReturnStatusOk() {
        client.put().uri("/address/1")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(addressUpdateDto), AddressUpdateDto.class)
                .exchange()
                .expectStatus().isOk();
    }
}