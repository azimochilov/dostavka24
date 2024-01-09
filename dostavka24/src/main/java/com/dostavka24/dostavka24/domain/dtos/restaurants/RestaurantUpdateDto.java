package com.dostavka24.dostavka24.domain.dtos.restaurants;

import com.dostavka24.dostavka24.domain.dtos.addresses.AddressCreationDto;
import com.dostavka24.dostavka24.domain.dtos.addresses.AddressUpdateDto;

public class RestaurantUpdateDto {
    private String name;
    private AddressUpdateDto address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressUpdateDto getAddress() {
        return address;
    }

    public void setAddress(AddressUpdateDto address) {
        this.address = address;
    }
}
