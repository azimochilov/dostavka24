package com.dostavka24.dostavka24.repository;

import com.dostavka24.dostavka24.domain.entities.restaurants.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByName(String name);

}