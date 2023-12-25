package com.dostavka24.dostavka24.domain.dtos.orders;

import com.dostavka24.dostavka24.domain.entities.addresses.Address;

public class OrderUpdateDto {
    private Address address;
    private String phone;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
