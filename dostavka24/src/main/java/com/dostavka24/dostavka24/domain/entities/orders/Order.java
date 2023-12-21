package com.dostavka24.dostavka24.domain.entities.orders;

import com.dostavka24.dostavka24.domain.entities.users.User;
import com.dostavka24.dostavka24.domain.enums.OrderStatus;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Instant createdAt;
    private Instant updatedAt;
    private Boolean isCart = true;
    private String yourAdress;
    private Float distance;
    private String phone;
    private Double totalPrice;
    private OrderStatus status;
    private Integer amountOfProducts;
    private String deliveryTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getCart() {
        return isCart;
    }

    public void setCart(Boolean cart) {
        isCart = cart;
    }

    public String getYourAdress() {
        return yourAdress;
    }

    public void setYourAdress(String yourAdress) {
        this.yourAdress = yourAdress;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Integer getAmountOfProducts() {
        return amountOfProducts;
    }

    public void setAmountOfProducts(Integer amountOfProducts) {
        this.amountOfProducts = amountOfProducts;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
