package com.dostavka24.dostavka24.services.commons;

import com.dostavka24.dostavka24.domain.entities.orders.Order;
import com.dostavka24.dostavka24.domain.entities.orders.OrderItem;
import com.dostavka24.dostavka24.repository.OrderItemRepository;
import com.dostavka24.dostavka24.service.commons.OrderDetailsProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderDetailsProcessorTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderDetailsProcessor orderDetailsProcessor;

    private Order order1;
    private Order order2;
    private OrderItem item1;
    private OrderItem item2;
    private OrderItem item3;

    @BeforeEach
    public void setUp() {
        // Setting up mock orders and items
        order1 = new Order();
        order1.setId(1L);

        order2 = new Order();
        order2.setId(2L);

        item1 = new OrderItem();
        item1.setOrder(order1);
        item1.setCount(3);

        item2 = new OrderItem();
        item2.setOrder(order1);
        item2.setCount(2);

        item3 = new OrderItem();
        item3.setOrder(order2);
        item3.setCount(5);
    }

    @Test
    public void getProductsByOrderId_WithValidOrderId_ShouldReturnCorrectCount() {
        // Mocking the behavior of orderItemRepository
        when(orderItemRepository.findAll()).thenReturn(Arrays.asList(item1, item2, item3));

        Integer count = orderDetailsProcessor.getProductsByOrderId(1L);

        assertEquals(5, count); // 3 from item1 and 2 from item2
    }

    @Test
    public void getCountOfProducts_WithListOfOrderItems_ShouldReturnCorrectCount() {
        List<OrderItem> orderItems = Arrays.asList(item1, item2);

        Integer count = orderDetailsProcessor.getCountOfProducts(orderItems);

        assertEquals(5, count); // 3 from item1 and 2 from item2
    }
}
