package com.dostavka24.dostavka24.domain.dtos.restaurants;

import com.dostavka24.dostavka24.domain.entities.addresses.Address;

public class RestaurantUpdateDto {
    private String name;
    private Address address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
