package com.dostavka24.dostavka24.rests.restaurants;
import com.dostavka24.dostavka24.domain.dtos.addresses.AddressCreationDto;
import com.dostavka24.dostavka24.domain.dtos.addresses.AddressUpdateDto;
import com.dostavka24.dostavka24.domain.dtos.restaurants.RestaurantCreationDto;
import com.dostavka24.dostavka24.domain.dtos.restaurants.RestaurantUpdateDto;
import com.dostavka24.dostavka24.domain.entities.restaurants.Restaurant;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class RestaurantControllerIntegrationTest {

    @Autowired
    WebApplicationContext wac;

    WebTestClient client;

    private RestaurantCreationDto restaurantCreationDto;
    private RestaurantUpdateDto restaurantUpdateDto;

    @BeforeEach
    void setUp() {
        client = MockMvcWebTestClient.bindToApplicationContext(this.wac)
                .configureClient()
                .baseUrl("http://localhost:8081")
                .build();

        restaurantCreationDto = new RestaurantCreationDto();
        AddressCreationDto addressDto = new AddressCreationDto();
        restaurantCreationDto.setAddress(addressDto);
        restaurantCreationDto.setName("EVOS");

        restaurantUpdateDto = new RestaurantUpdateDto();
        AddressUpdateDto addressUpdateDto = new AddressUpdateDto();
        restaurantUpdateDto.setName("SHASHLIK XONA");
        restaurantUpdateDto.setAddress(addressUpdateDto);

    }

    @Test
    @Order(1)
    @WithMockUser(username = "admin", authorities = "CREATE")
    void createRestaurant_ReturnStatusOk() {
        client.post().uri("/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(restaurantCreationDto), RestaurantCreationDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", authorities = "DELETE")
    void deleteRestaurant_ReturnStatusOk() {
        client.delete().uri("/restaurant/2")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin")
    void getAllRestaurants_ReturnStatusOk() {
        client.get().uri("/restaurant")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Restaurant.class);
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin")
    void getRestaurantById_ReturnStatusOk() {
        client.get().uri("/restaurant/2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(RestaurantCreationDto.class);
    }

    @Test
    @Order(5)
    @WithMockUser(username = "admin", authorities = "UPDATE")
    void updateRestaurant_ReturnStatusOk() {
        client.put().uri("/restaurant/3")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(restaurantUpdateDto), RestaurantUpdateDto.class)
                .exchange()
                .expectStatus().isOk();
    }
}
