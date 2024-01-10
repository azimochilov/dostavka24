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

    @PreAuthorize("hasAuthority('CREATE_ORDER_USER')")
    @PostMapping
    public ResponseEntity createOrder(@RequestBody OrderCreationDto orderCreationDto) {
        return ResponseEntity.ok(orderService.craeteOrder(orderCreationDto));
    }
    @PreAuthorize("hasAuthority('CHANGE_STATUS_WAITER')")
    @PatchMapping("/status/reject/{id}")
    public ResponseEntity changeStatusToReject(@PathVariable("id") Long orderId) {
        orderManagingService.rejectOrder(orderId);
        return ResponseEntity.ok("Order with given id Rejected!! ");
    }
    @PreAuthorize("hasAuthority('CHANGE_STATUS_WAITER')")
    @PatchMapping("/status/accept/{id}")
    public ResponseEntity changeStatusToAccept(@PathVariable("id") Long orderId) {
        orderManagingService.acceptOrder(orderId);
        return ResponseEntity.ok("Order with given id Accepted!! ");
    }
    @PreAuthorize("hasAuthority('CHANGE_STATUS_WAITER')")
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
    @PreAuthorize("hasAuthority('CHANGE_STATUS_WAITER')")
    @GetMapping("/{status}")
    public ResponseEntity<?> getAllByStatus(@RequestParam(name = "status", required = false) Boolean isActive) {
        if (isActive == null) {
            return ResponseEntity.ok(orderService.getAllOrder());
        }
        return ResponseEntity.ok(orderService.getAllByStatus(isActive));
    }
}

