package com.dostavka24.dostavka24.rest.orders;

import com.dostavka24.dostavka24.domain.dtos.orders.orderitem.OrderItemCreationDto;
import com.dostavka24.dostavka24.service.orders.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderItemController {
    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }


    @PostMapping("/order/items")
    public ResponseEntity create(@RequestBody OrderItemCreationDto orderItemCreationDto){
        orderItemService.createOrderItem(orderItemCreationDto);
        return ResponseEntity.ok(orderItemCreationDto);
    }

}
