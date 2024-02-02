package com.dostavka24.dostavka24.rests.users.roles;


import com.dostavka24.dostavka24.domain.dtos.users.roles.PrivilegeCreationDto;
import com.dostavka24.dostavka24.domain.dtos.users.roles.RoleCreationDto;
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

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class RoleControllerIntegrationTest {

    @Autowired
    WebApplicationContext wac;

    WebTestClient client;

    private RoleCreationDto roleCreationDto;

    @BeforeEach
    void setUp() {
        client = MockMvcWebTestClient.bindToApplicationContext(this.wac)
                .configureClient()
                .baseUrl("http://localhost:8081")
                .build();

        roleCreationDto =  RoleCreationDto.builder()
                .name("NewRole")
                .privileges(List.of(
                        new PrivilegeCreationDto("READ_PRIVILEGE")
                ))
                .build();
    }

    @Test
    @Order(1)
    @WithMockUser(username = "admin", authorities = "ROLE_PRIVILEGE_SERVICE")
    void createRole_ReturnStatusOk() {
        client.post().uri("/role")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(roleCreationDto), RoleCreationDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", authorities = "ROLE_PRIVILEGE_SERVICE")
    void deleteRole_ReturnStatusOk() {
        // Assuming a role with ID 1 exists and can be deleted
        client.delete().uri("/role/{id}", 1)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", authorities = "ROLE_PRIVILEGE_SERVICE")
    void getAllRoles_ReturnStatusOk() {
        client.get().uri("/role")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(List.class); // You may need a custom response type here
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", authorities = "ROLE_PRIVILEGE_SERVICE")
    void getRoleById_ReturnStatusOk() {
        // Assuming a role with ID 2 exists
        client.get().uri("/role/{id}", 8)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);

    }

    @Test
    @Order(5)
    @WithMockUser(username = "admin", authorities = "ROLE_PRIVILEGE_SERVICE")
    void updateRole_ReturnStatusOk() {
        // Assuming the RoleUpdateDto is similar to RoleCreationDto and role with ID 2 exists for update
        client.patch().uri("/role/{id}", 8)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(roleCreationDto), RoleCreationDto.class)
                .exchange()
                .expectStatus().isOk();
    }
}
