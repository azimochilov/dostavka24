package com.dostavka24.dostavka24.service.addresses;

import com.dostavka24.dostavka24.domain.dtos.addresses.AddressCreationDto;
import com.dostavka24.dostavka24.domain.dtos.addresses.AddressUpdateDto;
import com.dostavka24.dostavka24.domain.dtos.restaurants.RestaurantCreationDto;
import com.dostavka24.dostavka24.domain.dtos.restaurants.RestaurantUpdateDto;
import com.dostavka24.dostavka24.domain.entities.addresses.Address;
import com.dostavka24.dostavka24.exception.NotFoundException;
import com.dostavka24.dostavka24.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
    public Address create(AddressCreationDto addressCreationDto) {

        Address address = new Address();
        address.setCity(addressCreationDto.getCity());
        address.setStreet(addressCreationDto.getStreet());
        address.setLatitude(addressCreationDto.getLatitude());
        address.setLongitude(addressCreationDto.getLongitude());

        return addressRepository.save(address);
    }

    public void delete(Long id) {

        Address addressForDeletion = addressRepository.getById(id);

        addressRepository.delete(addressForDeletion);
    }

    public Address update(Long id, AddressUpdateDto updtAddress) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address not found with id: " + id));

        existingAddress.setLongitude(updtAddress.getLongitude());
        existingAddress.setLatitude(updtAddress.getLatitude());
        existingAddress.setCity(updtAddress.getCity());
        existingAddress.setStreet(updtAddress.getStreet());

        return addressRepository.save(existingAddress);
    }
    public List<Address> getAll() {

        return addressRepository.findAll();
    }

    public Address getById(Long id) {

        Address existingAddress = addressRepository.getById(id);
        return existingAddress;
    }

}
