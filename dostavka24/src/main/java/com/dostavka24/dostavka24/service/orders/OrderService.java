package com.dostavka24.dostavka24.service.orders;

import com.dostavka24.dostavka24.domain.dtos.orders.OrderCreationDto;
import com.dostavka24.dostavka24.domain.entities.orders.Order;
import com.dostavka24.dostavka24.repository.OrderItemRepository;
import com.dostavka24.dostavka24.repository.OrderRepository;
import com.dostavka24.dostavka24.repository.UserRepository;
import com.dostavka24.dostavka24.security.CustomUserDetail;
import com.dostavka24.dostavka24.service.commons.DeliveryCalService;
import com.dostavka24.dostavka24.service.users.UserService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;



@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemService orderItemService;
    private final CustomUserDetail customUserDetail;
    private final DeliveryCalService deliveryCalService;
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, UserService userService, OrderItemRepository orderItemRepository, OrderItemService orderItemService, CustomUserDetail customUserDetail, DeliveryCalService deliveryCalService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.orderItemRepository = orderItemRepository;
        this.orderItemService = orderItemService;
        this.customUserDetail = customUserDetail;
        this.deliveryCalService = deliveryCalService;
    }

    public Order craeteOrder(OrderCreationDto orderDto){
        Long currentUserId = customUserDetail.getCurrentUserId();

        Order userOrder = orderRepository.getByUserId(currentUserId);
        userOrder.setDistance(orderDto.getDistance());
        userOrder.setYourAdress(orderDto.getYourAdress());
        userOrder.setCreatedAt(Instant.now());
        userOrder.setPhone(orderDto.getPhone());
        userOrder.setAmountOfProducts(orderItemService.getProductsByOrderId(userOrder.getId()));
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
