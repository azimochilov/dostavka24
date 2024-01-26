package com.dostavka24.dostavka24.services.orders;


import com.dostavka24.dostavka24.domain.dtos.orders.orderitem.OrderItemCreationDto;
import com.dostavka24.dostavka24.domain.entities.orders.Order;
import com.dostavka24.dostavka24.domain.entities.orders.OrderItem;
import com.dostavka24.dostavka24.domain.entities.restaurants.Product;
import com.dostavka24.dostavka24.exception.NotFoundException;
import com.dostavka24.dostavka24.repository.OrderItemRepository;
import com.dostavka24.dostavka24.repository.OrderRepository;
import com.dostavka24.dostavka24.repository.ProductRepository;
import com.dostavka24.dostavka24.service.commons.DeliveryCalService;
import com.dostavka24.dostavka24.service.orders.OrderItemService;
import com.dostavka24.dostavka24.service.utils.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DeliveryCalService deliveryCalService;

    @InjectMocks
    private OrderItemService orderItemService;

    private Order mockOrder;
    private Product mockProduct;
    private OrderItemCreationDto orderItemDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock Order and Product setup
        mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setCart(true);
        mockOrder.setTotalPrice(0.0);
        mockOrder.setAmountOfProducts(0);

        mockProduct = new Product();
        mockProduct.setName("Test Product");
        mockProduct.setPrice(55);

        orderItemDto = new OrderItemCreationDto();
        orderItemDto.setName("Test Product");
        orderItemDto.setCount(2);


    }

    @Test
    void createOrderItem_WhenProductExists() {
            MockedStatic<SecurityUtils> utilities = Mockito.mockStatic(SecurityUtils.class);
            utilities.when(SecurityUtils::getCurrentUserId).thenReturn(1L);

        when(orderRepository.findAllByUserId(anyLong())).thenReturn(Collections.singletonList(mockOrder));
        when(orderRepository.getById(anyLong())).thenReturn(mockOrder);
        when(productRepository.findByName(orderItemDto.getName())).thenReturn(mockProduct);
        when(deliveryCalService.calculationOfTotalPriceOfProduct(anyInt(), anyInt())).thenReturn(20.0);
        when(orderItemRepository.save(any(OrderItem.class))).thenAnswer(i -> i.getArguments()[0]);

        OrderItem createdOrderItem = orderItemService.createOrderItem(orderItemDto);

        assertNotNull(createdOrderItem);
        assertEquals(orderItemDto.getName(), createdOrderItem.getName());
        assertEquals(orderItemDto.getCount(), createdOrderItem.getCount());
        assertEquals(mockProduct, createdOrderItem.getProduct());
        verify(orderRepository).save(any(Order.class));
        verify(orderItemRepository).save(any(OrderItem.class));
    }

    @Test
    void createOrderItem_WhenProductNotFound() {

            when(orderRepository.findAllByUserId(anyLong())).thenReturn(Collections.singletonList(mockOrder));
            when(orderRepository.getById(anyLong())).thenReturn(mockOrder);
            when(productRepository.findByName(orderItemDto.getName())).thenReturn(null);

            assertThrows(NotFoundException.class, () -> orderItemService.createOrderItem(orderItemDto));

    }
}
