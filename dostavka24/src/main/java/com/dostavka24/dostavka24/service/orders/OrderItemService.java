package com.dostavka24.dostavka24.service.orders;

import com.dostavka24.dostavka24.domain.dtos.orders.orderitem.OrderItemCreationDto;
import com.dostavka24.dostavka24.domain.entities.orders.Order;
import com.dostavka24.dostavka24.domain.entities.orders.OrderItem;
import com.dostavka24.dostavka24.domain.entities.restaurants.Product;
import com.dostavka24.dostavka24.exception.NotFoundException;
import com.dostavka24.dostavka24.repository.OrderItemRepository;
import com.dostavka24.dostavka24.repository.OrderRepository;
import com.dostavka24.dostavka24.repository.ProductRepository;
import com.dostavka24.dostavka24.security.CustomUserDetail;
import com.dostavka24.dostavka24.service.commons.DeliveryCalService;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final DeliveryCalService deliveryCalService;
    private final CustomUserDetail customUserDetail;

    public OrderItemService(OrderItemRepository orderItemRepository, ProductRepository productRepository, OrderRepository orderRepository, DeliveryCalService deliveryCalService, CustomUserDetail customUserDetail) {
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.deliveryCalService = deliveryCalService;
        this.customUserDetail = customUserDetail;
    }

    public OrderItem createOrderItem(OrderItemCreationDto orderItemDto){

        Long currentId = customUserDetail.getCurrentUserId();
        Order userOrder = orderRepository.getByUserId(currentId);

        Product product = productRepository.findByName(orderItemDto.getName());
        if(product == null){
            throw new NotFoundException("Product for given name not found!");
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(userOrder);
        orderItem.setCreatedAt(Instant.now());
        orderItem.setCount(orderItemDto.getCount());
        orderItem.setName(orderItemDto.getName());
        orderItem.setCreatedAt(Instant.now());
        orderItem.setProduct(product);

        Double total = deliveryCalService.calculationOfTotalPriceOfProduct(product.getPrice() , orderItemDto.getCount());
        userOrder.setTotalPrice(userOrder.getTotalPrice() + total);
        userOrder.setAmountOfProducts(userOrder.getAmountOfProducts()+orderItemDto.getCount());

        orderRepository.save(userOrder);

        return orderItemRepository.save(orderItem);
    }

}
