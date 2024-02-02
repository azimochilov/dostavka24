package com.dostavka24.dostavka24.rests.users;

import com.dostavka24.dostavka24.domain.dtos.addresses.AddressCreationDto;
import com.dostavka24.dostavka24.domain.dtos.addresses.AddressUpdateDto;
import com.dostavka24.dostavka24.domain.dtos.users.UserCreationDto;
import com.dostavka24.dostavka24.domain.dtos.users.UserUpdateDto;
import org.junit.jupiter.api.*;
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
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerIntegrationTest {

    @Autowired
    WebApplicationContext wac;

    WebTestClient client;

    private UserCreationDto userCreationDto;
    private UserUpdateDto userUpdateDto;
    private AddressCreationDto addressCreationDto;
    private AddressUpdateDto addressUpdateDto;

    @BeforeEach
    void setUp() {
        client = MockMvcWebTestClient.bindToApplicationContext(this.wac)
                .configureClient()
                .baseUrl("http://localhost:8081")
                .build();

        userUpdateDto = new UserUpdateDto();
        userUpdateDto.setUserName("testUser");
        userUpdateDto.setPassword("testPass1234");

        userCreationDto = new UserCreationDto();
        userCreationDto.setUserName("testUser");
        userCreationDto.setPassword("testPass1234");

        addressCreationDto = new AddressCreationDto();
        addressCreationDto.setCity("togin");
        addressCreationDto.setStreet("tutghhg");
        addressCreationDto.setLatitude(7.25);
        addressCreationDto.setLongitude(987.2);

        addressUpdateDto = new AddressUpdateDto();
        addressUpdateDto.setCity("toginadfasdf");
        addressUpdateDto.setStreet("tuasdftghhg");
        addressUpdateDto.setLatitude(7.25);
        addressUpdateDto.setLongitude(987.2);

        userCreationDto.setAdress(addressCreationDto);
        userUpdateDto.setAddress(addressUpdateDto);

    }

    @Test
    @Order(1)
    @WithMockUser(username = "admin", authorities = "CREATE_USER")
    void registerUser_ReturnStatusOk() {
        client.post().uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(userCreationDto), UserCreationDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(2)
    @WithMockUser(username = "azimochilov", authorities = "UPDATE_USER")
    void updateUser_ReturnStatusCreated() {

        client.patch().uri("/api/v1/users/13")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(userUpdateDto), UserUpdateDto.class)
                .exchange()
                .expectStatus().isCreated();
    }
}