package com.dostavka24.dostavka24.service.orders;

import com.dostavka24.dostavka24.domain.entities.orders.Order;
import com.dostavka24.dostavka24.domain.enums.OrderStatus;
import com.dostavka24.dostavka24.exception.NotFoundException;
import com.dostavka24.dostavka24.repository.OrderRepository;
import com.dostavka24.dostavka24.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderManagingService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderManagingService(OrderRepository orderRepository, UserRepository userRepository) {

        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public void acceptOrder(Long orderId){

        Order order = orderRepository.getById(orderId);
        if(order == null){
            throw new NotFoundException("Order not found!");
        }

        if(order.getCart() == true) {
            order.setStatus(OrderStatus.ACCEPTED);
        }
    }

    public void rejectOrder(Long orderId){

        Order order = orderRepository.getById(orderId);
        if(order == null){
            throw new NotFoundException("Order not found!");
        }

        if(order.getCart() == true) {
            order.setStatus(OrderStatus.REJECTED);
            order.setCart(false);
            orderRepository.save(order);
            Order newOrder = new Order();
            newOrder.setCart(true);
            newOrder.setUser(order.getUser());
            orderRepository.save(newOrder);

        }
    }

    public void deliverOrder(Long orderId){

        Order order = orderRepository.getById(orderId);
        if(order == null){
            throw new NotFoundException("Order not found!");
        }

        if(order.getCart() == true) {
            order.setStatus(OrderStatus.DELIVERED);
            order.setCart(false);
            orderRepository.save(order);
            Order newOrder = new Order();
            newOrder.setUser(order.getUser());
            orderRepository.save(newOrder);

        }
    }
}
