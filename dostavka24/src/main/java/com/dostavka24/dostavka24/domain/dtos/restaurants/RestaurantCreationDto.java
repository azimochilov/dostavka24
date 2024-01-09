package com.dostavka24.dostavka24.domain.dtos.restaurants;

import com.dostavka24.dostavka24.domain.dtos.addresses.AddressCreationDto;
import com.dostavka24.dostavka24.domain.entities.addresses.Address;

public class RestaurantCreationDto {
    private String name;
    private AddressCreationDto address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressCreationDto getAddress() {
        return address;
    }

    public void setAddress(AddressCreationDto address) {
        this.address = address;
    }
}
