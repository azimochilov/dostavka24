package com.dostavka24.dostavka24.rest.orders;


import com.dostavka24.dostavka24.domain.dtos.orders.OrderCreationDto;
import com.dostavka24.dostavka24.service.orders.OrderManagingService;
import com.dostavka24.dostavka24.service.orders.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderManagingService orderManagingService;

    public OrderController(OrderService orderService, OrderManagingService orderManagingService) {
        this.orderService = orderService;
        this.orderManagingService = orderManagingService;
    }


    @PostMapping("")
    public ResponseEntity createOrder(@RequestBody OrderCreationDto orderCreationDto) {
        return ResponseEntity.ok(orderService.craeteOrder(orderCreationDto));
    }

    @PatchMapping("/status/reject")
    public ResponseEntity changeStatusToReject(@RequestBody Long orderId) {
        orderManagingService.rejectOrder(orderId);
        return ResponseEntity.ok("Order with given id Rejected!! ");
    }

    @PatchMapping("/status/accept")
    public ResponseEntity changeStatusToAccept(@RequestBody Long orderId) {
        orderManagingService.acceptOrder(orderId);
        return ResponseEntity.ok("Order with given id Accepted!! ");
    }

    @PatchMapping("/status/deliver/{id}")
    public ResponseEntity changeStatusToDeliver(@PathVariable("id") Long orderId) {
        orderManagingService.deliverOrder(orderId);
        return ResponseEntity.ok("Order with given id Delivered!! ");
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(orderService.getAllOrder());
    }

    @GetMapping
    public ResponseEntity<?> getAllByStatus(@RequestParam(name = "status", required = false) Boolean isActive) {
        if (isActive == null) {
            return ResponseEntity.ok(orderService.getAllOrder());
        }
        return ResponseEntity.ok(orderService.getAllByStatus(isActive));
    }
}

