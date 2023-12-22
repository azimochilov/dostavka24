package com.dostavka24.dostavka24.domain.dtos.products;

import com.dostavka24.dostavka24.domain.enums.ProductStatus;

import java.time.Instant;

public class ProductCreationDto {
    private String name;
    private Integer price;
    private ProductStatus productStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }
}
