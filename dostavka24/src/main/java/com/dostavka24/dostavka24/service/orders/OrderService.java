package com.dostavka24.dostavka24.service.orders;

import com.dostavka24.dostavka24.domain.dtos.orders.OrderCreationDto;
import com.dostavka24.dostavka24.domain.entities.orders.Order;
import com.dostavka24.dostavka24.exception.NotFoundException;
import com.dostavka24.dostavka24.repository.OrderRepository;
import com.dostavka24.dostavka24.service.addresses.AddressService;
import com.dostavka24.dostavka24.service.commons.DeliveryCalService;
import com.dostavka24.dostavka24.service.commons.OrderDetailsProcessor;
import com.dostavka24.dostavka24.service.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;



@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final AddressService addressService;
    private final DeliveryCalService deliveryCalService;
    private final OrderDetailsProcessor orderDetailsProcessor;
    public OrderService(OrderRepository orderRepository,
                        AddressService addressService,
                        DeliveryCalService deliveryCalService,
                        OrderDetailsProcessor orderDetailsProcessor
    ) {

        this.orderRepository = orderRepository;
        this.addressService = addressService;
        this.deliveryCalService = deliveryCalService;
        this.orderDetailsProcessor = orderDetailsProcessor;

    }

    public Order craeteOrder(OrderCreationDto orderDto){
        //current user
        Long currentUserId = SecurityUtils.getCurrentUserId();

        List<Order> userOrders = orderRepository.findAllByUserId(currentUserId);
        Order userOrder = userOrders.stream()
                .filter(Order::getCart)
                .findFirst()
                .orElseGet(() -> new Order()); // Create new order if not found

        userOrder.setAddress(addressService.create(orderDto.getAddress()));
        userOrder.setCreatedAt(Instant.now());
        userOrder.setPhone(orderDto.getPhone());

        //calculation of delivery time
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
