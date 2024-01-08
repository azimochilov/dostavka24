package com.dostavka24.dostavka24.rest.orders;


import com.dostavka24.dostavka24.domain.dtos.orders.OrderCreationDto;
import com.dostavka24.dostavka24.service.orders.OrderManagingService;
import com.dostavka24.dostavka24.service.orders.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderManagingService orderManagingService;

    public OrderController(OrderService orderService, OrderManagingService orderManagingService) {
        this.orderService = orderService;
        this.orderManagingService = orderManagingService;
    }


    @PostMapping
    public ResponseEntity createOrder(@RequestBody OrderCreationDto orderCreationDto) {
        return ResponseEntity.ok(orderService.craeteOrder(orderCreationDto));
    }

    @PatchMapping("/status/reject/{id}")
    public ResponseEntity changeStatusToReject(@PathVariable("id") Long orderId) {
        orderManagingService.rejectOrder(orderId);
        return ResponseEntity.ok("Order with given id Rejected!! ");
    }

    @PatchMapping("/status/accept/{id}")
    public ResponseEntity changeStatusToAccept(@PathVariable("id") Long orderId) {
        orderManagingService.acceptOrder(orderId);
        return ResponseEntity.ok("Order with given id Accepted!! ");
    }

    @PatchMapping("/status/deliver/{id}")
    public ResponseEntity changeStatusToDeliver(@PathVariable("id") Long orderId) {
        orderManagingService.deliverOrder(orderId);
        return ResponseEntity.ok("Order with given id Delivered!! ");
    }
    @PreAuthorize("hasAuthority('ORDER_SERVICE')")
    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(orderService.getAllOrder());
    }

    @GetMapping("/{status}")
    public ResponseEntity<?> getAllByStatus(@RequestParam(name = "status", required = false) Boolean isActive) {
        if (isActive == null) {
            return ResponseEntity.ok(orderService.getAllOrder());
        }
        return ResponseEntity.ok(orderService.getAllByStatus(isActive));
    }
}

