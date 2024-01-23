package com.dostavka24.dostavka24.services.addresses;

import com.dostavka24.dostavka24.domain.dtos.addresses.AddressCreationDto;
import com.dostavka24.dostavka24.domain.dtos.addresses.AddressUpdateDto;
import com.dostavka24.dostavka24.domain.entities.addresses.Address;
import com.dostavka24.dostavka24.repository.AddressRepository;
import com.dostavka24.dostavka24.service.addresses.AddressService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @InjectMocks
    private AddressService addressService;
    @Mock
    private AddressRepository addressRepository;

    private Address address = new Address();
    private AddressCreationDto addressCreationDto = new AddressCreationDto();;
    private AddressUpdateDto addressUpdateDto = new AddressUpdateDto();
    private List<Address> addresses = new ArrayList<>();

    @BeforeEach
    public void setUp(){

        //Creation Dto
        addressCreationDto.setCity("TestCity");
        addressCreationDto.setLatitude(1.0);
        addressCreationDto.setLongitude(1.0);
        addressCreationDto.setStreet("TestStreet");

        //Update Dto
        addressUpdateDto.setCity("TestUpdateCity");
        addressUpdateDto.setLatitude(3.0);
        addressUpdateDto.setLongitude(3.0);
        addressUpdateDto.setStreet("TestUpdateStreet");

        address.setId(1L);
        address.setStreet("TestStreetTashkent");
        address.setCity("TestCityTashkent");
        address.setLatitude(1.0);
        address.setLongitude(1.0);

        addresses.add(address);
    }


    @Test
    public void AddressService_craeteAddress(){

        when(addressRepository.save(any())).thenAnswer(invocation -> {
            Address savedAddress = invocation.getArgument(0);
            savedAddress.setId(1L);
            return savedAddress;
        });

        Address addressT = addressService.create(addressCreationDto);

        assertAll(
                () -> Assertions.assertThat(addressT).isNotNull(),
                () -> Assertions.assertThat(addressT.getStreet()).isEqualTo(addressCreationDto.getStreet()),
                () -> Assertions.assertThat(addressT.getLatitude()).isEqualTo(addressCreationDto.getLatitude()),
                () -> Assertions.assertThat(addressT.getLongitude()).isEqualTo(addressCreationDto.getLongitude())
        );
    }

    @Test
    public void AddressService_deleteAddress(){

        when(addressRepository.getById(1L)).thenReturn(address);

        assertAll(
                () -> addressService.delete(1L)
        );
    }

    @Test
    public void AddressService_updateAddress(){
        when(addressRepository.getById(any())).thenReturn(address);
        when(addressRepository.save(any())).thenReturn(address);

        Address addressTest = addressService.update(1L, addressUpdateDto);

        assertAll(
                () -> Assertions.assertThat(addressTest).isNotNull(),
                () -> Assertions.assertThat(addressTest.getStreet()).isEqualTo(addressUpdateDto.getStreet()),
                () -> Assertions.assertThat(addressTest.getLatitude()).isEqualTo(addressUpdateDto.getLatitude()),
                () -> Assertions.assertThat(addressTest.getLongitude()).isEqualTo(addressUpdateDto.getLongitude())
        );
    }

    @Test
    public void AddressService_getAddressById(){
        when(addressRepository.getById(1L)).thenReturn(address);
        Address addressTest = addressService.getById(1L);

        assertAll(
                () -> Assertions.assertThat(addressTest).isNotNull(),
                () -> Assertions.assertThat(addressTest.getStreet()).isEqualTo(address.getStreet()),
                () -> Assertions.assertThat(addressTest.getLatitude()).isEqualTo(address.getLatitude()),
                () -> Assertions.assertThat(addressTest.getLongitude()).isEqualTo(address.getLongitude()),
                () -> Assertions.assertThat(addressTest.getCity()).isEqualTo(address.getCity())
        );
    }

    @Test
    public void AddressService_getAll(){
        when(addressRepository.findAll()).thenReturn(addresses);
        List<Address> addressesList = addressRepository.findAll();

        assertAll(
                () -> Assertions.assertThat(addressesList).isNotNull()
        );
    }

}
