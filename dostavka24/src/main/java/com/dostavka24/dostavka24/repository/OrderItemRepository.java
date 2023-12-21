package com.dostavka24.dostavka24.repository;

import com.dostavka24.dostavka24.domain.entities.orders.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT COUNT(oi) FROM OrderItem oi WHERE oi.order.id = :orderId")
    Integer countByOrderId(@Param("orderId") Long orderId);
}

