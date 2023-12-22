package com.dostavka24.dostavka24.service.orders;

import com.dostavka24.dostavka24.repository.OrderItemRepository;
import com.dostavka24.dostavka24.repository.OrderRepository;
import com.dostavka24.dostavka24.repository.ProductRepository;
import com.dostavka24.dostavka24.service.users.UserService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;

    public OrderItemService(OrderItemRepository orderItemRepository, ProductRepository productRepository, OrderRepository orderRepository, UserService userService) {
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public OrderItem createOrderItem(Product product){
        String currentUser = SecurityUtils.getUserName();
        Long currentId = userService.getId(currentUser);
        Order userOrder = orderRepository.getByUserId(currentId);

        Menu menu = menuRepository.findByName(product.getName());
        if(menu == null){
            throw new NotFoundException("Product for given name not found!");
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(userOrder);
        orderItem.setCreatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        orderItem.setCount(product.getCount());
        orderItem.setName(product.getName());
        orderItem.setCreatedAt(Date.from(Instant.now()));
        orderItem.setMenu(menu);
        Double total = (menu.getPrice()* product.getCount());
        if(userOrder.getTotalPrice() == null){
            userOrder.setTotalPrice(total);
            userOrder.setAmountOfProducts(product.getCount());
        }else {
            userOrder.setTotalPrice(userOrder.getTotalPrice() + total);
            userOrder.setAmountOfProducts(userOrder.getAmountOfProducts()+product.getCount());
        }
        orderRepository.save(userOrder);
        return orderItemRepository.save(orderItem);
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
