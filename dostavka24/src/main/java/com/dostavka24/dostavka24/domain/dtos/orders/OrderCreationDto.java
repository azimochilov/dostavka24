package com.dostavka24.dostavka24.domain.dtos.orders;

import com.dostavka24.dostavka24.domain.dtos.addresses.AddressCreationDto;

public class OrderCreationDto {

    private AddressCreationDto address;
    private String phone;

    public AddressCreationDto getAddress() {
        return address;
    }

    public void setAddress(AddressCreationDto address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
