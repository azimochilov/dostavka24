package com.dostavka24.dostavka24.service.orders;

import com.dostavka24.dostavka24.domain.dtos.orders.OrderCreationDto;
import com.dostavka24.dostavka24.domain.entities.orders.Order;
import com.dostavka24.dostavka24.repository.OrderRepository;
import com.dostavka24.dostavka24.security.CustomUserDetailService;
import com.dostavka24.dostavka24.service.commons.DeliveryCalService;
import com.dostavka24.dostavka24.service.commons.OrderDetailsProcessor;
import com.dostavka24.dostavka24.service.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;



@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final CustomUserDetailService customUserDetail;
    private final DeliveryCalService deliveryCalService;
    private final OrderDetailsProcessor orderDetailsProcessor;
    public OrderService(OrderRepository orderRepository, OrderItemService orderItemService, CustomUserDetailService customUserDetail, DeliveryCalService deliveryCalService, OrderDetailsProcessor orderDetailsProcessor) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.customUserDetail = customUserDetail;
        this.deliveryCalService = deliveryCalService;
        this.orderDetailsProcessor = orderDetailsProcessor;
    }

    public Order craeteOrder(OrderCreationDto orderDto){
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Order userOrder = orderRepository.getByUserId(currentUserId);
        userOrder.setAddress(orderDto.getAddress());
        userOrder.setCreatedAt(Instant.now());
        userOrder.setPhone(orderDto.getPhone());
        userOrder.setAmountOfProducts(orderDetailsProcessor.getProductsByOrderId(userOrder.getId()));
        userOrder.setDeliveryTime(deliveryCalService.calculateEstimatedDeliveryTime(userOrder.getAmountOfProducts(),4));

        orderRepository.save(userOrder);

        return userOrder;
    }



    public List<Order> getAllOrder(){

        return orderRepository.findAll();
    }

    public List<Order> getAllByStatus(Boolean status){

        return orderRepository.findAllByIsCart(status);
    }

}
