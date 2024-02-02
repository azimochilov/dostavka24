package com.dostavka24.dostavka24.services.orders;

import com.dostavka24.dostavka24.domain.entities.orders.Order;
import com.dostavka24.dostavka24.domain.enums.OrderStatus;
import com.dostavka24.dostavka24.exception.NotFoundException;
import com.dostavka24.dostavka24.repository.OrderRepository;
import com.dostavka24.dostavka24.service.orders.OrderManagingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderManagingServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderManagingService orderManagingService;

    private Order mockOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setCart(true);
        mockOrder.setStatus(OrderStatus.ACCEPTED);
    }

    @Test
    void acceptOrder_WhenOrderExists() {
        when(orderRepository.getById(mockOrder.getId())).thenReturn(mockOrder);

        orderManagingService.acceptOrder(mockOrder.getId());

        assertEquals(OrderStatus.ACCEPTED, mockOrder.getStatus());
        verify(orderRepository, times(1)).getById(mockOrder.getId());
    }

    @Test
    void acceptOrder_WhenOrderNotFound() {
        when(orderRepository.getById(anyLong())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> orderManagingService.acceptOrder(2L));
    }

    @Test
    void rejectOrder_WhenOrderExists() {
        when(orderRepository.getById(mockOrder.getId())).thenReturn(mockOrder);

        orderManagingService.rejectOrder(mockOrder.getId());

        assertEquals(OrderStatus.REJECTED, mockOrder.getStatus());
        assertFalse(mockOrder.getCart());
        verify(orderRepository, times(2)).save(any(Order.class));
    }

    @Test
    void rejectOrder_WhenOrderNotFound() {
        when(orderRepository.getById(anyLong())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> orderManagingService.rejectOrder(2L));
    }

    @Test
    void deliverOrder_WhenOrderExists() {
        when(orderRepository.getById(mockOrder.getId())).thenReturn(mockOrder);

        orderManagingService.deliverOrder(mockOrder.getId());

        assertEquals(OrderStatus.DELIVERED, mockOrder.getStatus());
        assertFalse(mockOrder.getCart());
        verify(orderRepository, times(2)).save(any(Order.class));
    }

    @Test
    void deliverOrder_WhenOrderNotFound() {
        when(orderRepository.getById(anyLong())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> orderManagingService.deliverOrder(2L));
    }
}