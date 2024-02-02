package com.dostavka24.dostavka24.rests.users.roles;

import com.dostavka24.dostavka24.domain.dtos.users.roles.PrivilegeCreationDto;
import com.dostavka24.dostavka24.domain.entities.users.Privilege;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(OrderAnnotation.class)
public class PrivilegeControllerIntegrationTest {

    @Autowired
    WebApplicationContext wac;

    WebTestClient client;

    private PrivilegeCreationDto privilegeCreationDto;

    @BeforeEach
    void setUp() {
        client = MockMvcWebTestClient.bindToApplicationContext(this.wac)
                .configureClient()
                .baseUrl("http://localhost:8081")
                .build();

        privilegeCreationDto = new PrivilegeCreationDto("READ_PRIVILEGE");

    }

    @Test
    @Order(1)
    @WithMockUser(username = "admin", authorities = "ROLE_PRIVILEGE_SERVICE")
    void createPrivilege_ReturnStatusOk() {
        client.post().uri("/privilege")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(privilegeCreationDto), PrivilegeCreationDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", authorities = "ROLE_PRIVILEGE_SERVICE")
    void getAllPrivileges_ReturnStatusOk() {
        client.get().uri("/privilege")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Privilege.class);

    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", authorities = "ROLE_PRIVILEGE_SERVICE")
    void getPrivilegeById_ReturnStatusOk() {
        client.get().uri("/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(PrivilegeCreationDto.class);
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", authorities = "ROLE_PRIVILEGE_SERVICE")
    void deletePrivilege_ReturnStatusOk() {
        client.delete().uri("/{id}", 1)
                .exchange()
                .expectStatus().isOk();
    }
}
