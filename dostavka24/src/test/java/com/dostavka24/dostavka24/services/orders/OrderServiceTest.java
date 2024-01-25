package com.dostavka24.dostavka24.services.orders;

import com.dostavka24.dostavka24.domain.dtos.addresses.AddressCreationDto;
import com.dostavka24.dostavka24.domain.dtos.orders.OrderCreationDto;
import com.dostavka24.dostavka24.domain.entities.addresses.Address;
import com.dostavka24.dostavka24.domain.entities.orders.Order;
import com.dostavka24.dostavka24.domain.entities.users.User;
import com.dostavka24.dostavka24.domain.enums.OrderStatus;
import com.dostavka24.dostavka24.repository.OrderRepository;
import com.dostavka24.dostavka24.security.UserPrincipal;
import com.dostavka24.dostavka24.service.addresses.AddressService;
import com.dostavka24.dostavka24.service.commons.DeliveryCalService;
import com.dostavka24.dostavka24.service.commons.OrderDetailsProcessor;
import com.dostavka24.dostavka24.service.orders.OrderService;
import com.dostavka24.dostavka24.service.utils.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private AddressService addressService;
    @Mock
    private DeliveryCalService deliveryCalService;
    @Mock
    private OrderDetailsProcessor orderDetailsProcessor;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private User user;
    private Address address;
    private OrderCreationDto orderCreationDto;
    private AddressCreationDto addressCreationDto;
    private UserPrincipal userPrincipal;

    @BeforeEach
    public void setUp() {
        // Initialize User
        user = new User();
        user.setId(1L);
        user.setUserName("testUser");
        user.setPassword("password");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@example.com");
        user.setActive(true);
        user.setRegisteredAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        userPrincipal = UserPrincipal.builder()
                .userId(1L)
                .build();


        // Initialize Address
        address = new Address();
        address.setId(1L);
        address.setStreet("123 Test Street");
        address.setCity("Test City");
        address.setLatitude(10.0);
        address.setLongitude(20.0);

        // Initialize Order
        order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setCreatedAt(Instant.now());
        order.setUpdatedAt(Instant.now());
        order.setCart(true);
        order.setPhone("1234567890");
        order.setTotalPrice(100.0);
        order.setStatus(OrderStatus.ACCEPTED); // Assuming OrderStatus is an enum
        order.setAmountOfProducts(5);
        order.setDeliveryTime("30 mins");
        order.setAddress(address);
        // Assuming Restaurant is set separately or not needed for this test

        // Initialize AddressCreationDto
        addressCreationDto = new AddressCreationDto();
        addressCreationDto.setStreet("123 Test Street");
        addressCreationDto.setCity("Test City");
        addressCreationDto.setLatitude(10.0);
        addressCreationDto.setLongitude(20.0);

        // Initialize OrderCreationDto
        orderCreationDto = new OrderCreationDto();
        orderCreationDto.setAddress(addressCreationDto);
        orderCreationDto.setPhone("1234567890");


    }

    @Test
    void shouldCreateOrderSuccessfully() {


        try (MockedStatic<DeliveryCalService> mockedDeliveryCalService = Mockito.mockStatic(DeliveryCalService.class)) {
            // Define behavior for the static method
            mockedDeliveryCalService.when(() -> DeliveryCalService.calculateEstimatedDeliveryTime(anyInt(), anyInt()))
                    .thenReturn("30 mins");

            // Mock other interactions
            MockedStatic<SecurityUtils> utilities = Mockito.mockStatic(SecurityUtils.class);
            utilities.when(SecurityUtils::getCurrentUserId).thenReturn(1L);
            when(orderRepository.findAllByUserId(anyLong())).thenReturn(Collections.singletonList(order));
            when(orderRepository.getByUserId(anyLong())).thenReturn(order);
            when(addressService.create(any(AddressCreationDto.class))).thenReturn(address);
            when(orderDetailsProcessor.getProductsByOrderId(anyLong())).thenReturn(10);

            // Call the method under test
            Order createdOrder = orderService.craeteOrder(orderCreationDto);

            // Assertions
            assertNotNull(createdOrder);
            assertEquals(orderCreationDto.getPhone(), createdOrder.getPhone());
            assertEquals(10, createdOrder.getAmountOfProducts());
            assertEquals("30 mins", createdOrder.getDeliveryTime());
            verify(orderRepository).save(any(Order.class));

            // Close the static mocks
            utilities.close();
        }
    }


    @Test
    void shouldReturnAllOrders() {
        List<Order> orders = Arrays.asList(new Order(), new Order()); // Sample list of orders
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> retrievedOrders = orderService.getAllOrder();

        assertNotNull(retrievedOrders);
        assertEquals(2, retrievedOrders.size());
        verify(orderRepository).findAll();
    }

    @Test
    void shouldReturnOrdersByStatus() {
        List<Order> orders = Arrays.asList(new Order(), new Order()); // Sample list of orders
        Boolean status = true; // Example status to filter by

        when(orderRepository.findAllByIsCart(status)).thenReturn(orders);

        List<Order> retrievedOrders = orderService.getAllByStatus(status);

        assertNotNull(retrievedOrders);
        assertEquals(2, retrievedOrders.size());
        verify(orderRepository).findAllByIsCart(status);
    }


}

