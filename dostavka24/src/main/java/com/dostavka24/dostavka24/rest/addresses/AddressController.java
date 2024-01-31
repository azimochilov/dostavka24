package com.dostavka24.dostavka24.rest.addresses;

import com.dostavka24.dostavka24.domain.dtos.addresses.AddressCreationDto;
import com.dostavka24.dostavka24.domain.dtos.addresses.AddressUpdateDto;
import com.dostavka24.dostavka24.domain.entities.addresses.Address;
import com.dostavka24.dostavka24.service.addresses.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

//    public AddressController(AddressService addressService) {
//        this.addressService = addressService;
//    }
    @PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping
    public ResponseEntity create(@RequestBody AddressCreationDto addressCreationDto){
        addressService.create(addressCreationDto);
        return ResponseEntity.ok(addressCreationDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity getAll(){
        List<Address> addresses = addressService.getAll();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Address address = addressService.getById(id);

        AddressCreationDto addressCreationDto = new AddressCreationDto();
        addressCreationDto.setCity(address.getCity());
        addressCreationDto.setStreet(address.getStreet());
        addressCreationDto.setLatitude(address.getLatitude());
        addressCreationDto.setLongitude(address.getLongitude());

        return ResponseEntity.ok(addressCreationDto);
    }
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id,@RequestBody AddressUpdateDto addressUpdateDto){
        Address updatedAddress = addressService.update(id,addressUpdateDto);
        return ResponseEntity.ok(updatedAddress);
    }
}
