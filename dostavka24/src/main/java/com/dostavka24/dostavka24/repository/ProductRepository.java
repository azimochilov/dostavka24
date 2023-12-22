package com.dostavka24.dostavka24.repository;

import com.dostavka24.dostavka24.domain.entities.restaurants.Product;
import com.dostavka24.dostavka24.domain.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByName(String name);
    Product getById(Long id);
    List<Product> getAllByProductStatus(ProductStatus status);

}