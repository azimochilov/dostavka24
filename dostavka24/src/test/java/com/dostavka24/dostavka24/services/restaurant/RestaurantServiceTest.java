package com.dostavka24.dostavka24.services.restaurant;

import com.dostavka24.dostavka24.domain.dtos.addresses.AddressCreationDto;
import com.dostavka24.dostavka24.domain.dtos.addresses.AddressUpdateDto;
import com.dostavka24.dostavka24.domain.dtos.restaurants.RestaurantCreationDto;
import com.dostavka24.dostavka24.domain.dtos.restaurants.RestaurantUpdateDto;
import com.dostavka24.dostavka24.domain.entities.addresses.Address;
import com.dostavka24.dostavka24.domain.entities.orders.Order;
import com.dostavka24.dostavka24.domain.entities.restaurants.Restaurant;
import com.dostavka24.dostavka24.domain.entities.users.Role;
import com.dostavka24.dostavka24.domain.entities.users.User;
import com.dostavka24.dostavka24.domain.enums.OrderStatus;
import com.dostavka24.dostavka24.exception.NotFoundException;
import com.dostavka24.dostavka24.repository.OrderRepository;
import com.dostavka24.dostavka24.repository.RestaurantRepository;
import com.dostavka24.dostavka24.service.addresses.AddressService;
import com.dostavka24.dostavka24.service.commons.DistanceCalService;
import com.dostavka24.dostavka24.service.restaurant.RestaurantService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private DistanceCalService distanceCalService;

    @Mock
    private AddressService addressService;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    private Restaurant restaurant = new Restaurant();
    private RestaurantCreationDto restaurantCreationDto = new RestaurantCreationDto();
    private RestaurantUpdateDto restaurantUpdateDto = new RestaurantUpdateDto();
    private Order order = new Order();
    private Address address = new Address();
    private AddressCreationDto addressCreationDto = new AddressCreationDto();
    private AddressUpdateDto addressUpdateDto = new AddressUpdateDto();
    private User user = new User();
    private Role role = new Role();
    private List<Order> orders = new ArrayList<>();
    @BeforeEach
    public void setUp(){

        //Address
        address.setId(1L);
        address.setStreet("TestStreetTashkent");
        address.setCity("TestCityTashkent");
        address.setLatitude(1.0);
        address.setLongitude(1.0);

        //Address Creation Dto
        addressCreationDto.setCity("TestCity");
        addressCreationDto.setLatitude(1.0);
        addressCreationDto.setLongitude(1.0);
        addressCreationDto.setStreet("TestStreet");

        //Address Update Dto
        addressUpdateDto.setCity("TestUpdateCity");
        addressUpdateDto.setLatitude(3.0);
        addressUpdateDto.setLongitude(3.0);
        addressUpdateDto.setStreet("TestUpdateStreet");

        //Role
        role.setId(1L);
        role.setName("ADMIN");

        //User
        user.setId(1L);
        user.setEmail("azimochilov29");
        user.setFirstName("azim");
        user.setLastName("ochilov");
        user.setActive(true);
        user.setAddress(address);
        user.setPassword("Yaironman@.26");
        user.setUserName("azim");
        user.setUpdatedAt(Instant.now());
        user.setRegisteredAt(Instant.now());
        user.setRole(role);

        //Order
        order.setId(1L);
        order.setCart(true);
        order.setAddress(address);
        order.setPhone("+998934542418");
        order.setStatus(OrderStatus.ACCEPTED);
        order.setAmountOfProducts(10);
        order.setCreatedAt(Instant.now());
        order.setDeliveryTime("20min");
        order.setTotalPrice(5.0);
        order.setUpdatedAt(Instant.now());
        order.setUser(user);

        //Restaurant
        restaurant.setId(1L);
        restaurant.setName("EVOS");
        restaurant.setAddress(address);
        restaurant.setOrders(orders);

        //List Of Orders
        orders.add(order);

        //Restaurant Creation Dto
        restaurantCreationDto.setAddress(addressCreationDto);
        restaurantCreationDto.setName("EVOS");

        //Restaurant Update Dto
        restaurantUpdateDto.setAddress(addressUpdateDto);
        restaurantUpdateDto.setName("MAXWAY");

    }



    @Test
    public void createRestaurant_NewRestaurant_ShouldSaveRestaurant() {
        when(restaurantRepository.findByName(anyString())).thenReturn(null);
        when(addressService.create(any())).thenReturn(address);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        Restaurant result = restaurantService.create(restaurantCreationDto);

        assertNotNull(result);
        assertEquals("EVOS", result.getName());
        verify(restaurantRepository).save(any(Restaurant.class));
    }

    @Test
    public void createRestaurant_ExistingRestaurant_ShouldThrowNotFoundException() {
        when(restaurantRepository.findByName(anyString())).thenReturn(restaurant);

        assertThrows(NotFoundException.class, () -> {
            restaurantService.create(restaurantCreationDto);
        });
    }

    @Test
    public void deleteRestaurant_ExistingRestaurant_ShouldDeleteRestaurant() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        doNothing().when(restaurantRepository).delete(any(Restaurant.class));

        restaurantService.delete(1L);

        verify(restaurantRepository).delete(any(Restaurant.class));
    }

    @Test
    public void updateRestaurant_ExistingRestaurant_ShouldUpdateRestaurant() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(addressService.update(anyLong(), any())).thenReturn(address);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        Restaurant updatedRestaurant = restaurantService.update(1L, restaurantUpdateDto);

        assertNotNull(updatedRestaurant);
        assertEquals("MAXWAY", updatedRestaurant.getName());
        verify(restaurantRepository).save(any(Restaurant.class));
    }

    @Test
    public void getAllRestaurants_ShouldReturnListOfRestaurants() {
        when(restaurantRepository.findAll()).thenReturn(Collections.singletonList(restaurant));

        List<Restaurant> restaurants = restaurantService.getAll();

        assertFalse(restaurants.isEmpty());
        assertEquals(1, restaurants.size());
        assertEquals("EVOS", restaurants.get(0).getName());
    }

    @Test
    public void getRestaurantById_ExistingRestaurant_ShouldReturnRestaurant() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));

        Restaurant foundRestaurant = restaurantService.getById(1L);

        assertNotNull(foundRestaurant);
        assertEquals("EVOS", foundRestaurant.getName());
    }

}
