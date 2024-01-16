package com.dostavka24.dostavka24.repository;

import com.dostavka24.dostavka24.domain.entities.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Order getByUserId (Long userId);
    Order getById(Long orderId);
    List<Order> findAllByIsCart(Boolean status);
    Order getOrderByUserId(Long id);
    List<Order> findAllByUserId(Long userId);
    Order findFirstByUserOrderByCreatedAtDesc(Long id);
}