package com.dostavka24.dostavka24.repository;

import com.dostavka24.dostavka24.domain.entities.restaurants.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByName(String name);
    Restaurant getById(Long id);
}
