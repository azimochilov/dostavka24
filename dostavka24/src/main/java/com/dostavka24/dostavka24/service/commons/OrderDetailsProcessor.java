package com.dostavka24.dostavka24.service.commons;

import com.dostavka24.dostavka24.domain.entities.orders.OrderItem;
import com.dostavka24.dostavka24.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class OrderDetailsProcessor {
    private final OrderItemRepository orderItemRepository;

    public OrderDetailsProcessor(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public Integer getProductsByOrderId(Long orderId){
        List<OrderItem> relatedItems = new ArrayList<>();

        for(OrderItem orderItem : orderItemRepository.findAll()){
            if(orderItem.getOrder().getId() == orderId){
                relatedItems.add(orderItem);
            }
        }

        return getCountOfProducts(relatedItems);
    }

    public Integer getCountOfProducts(List<OrderItem> orderItems){
        Integer count = 0;
        for(OrderItem orderItem : orderItems){
            count += orderItem.getCount();
        }
        return count;
    }
}