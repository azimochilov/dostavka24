package com.dostavka24.dostavka24.rest.addresses;

import com.dostavka24.dostavka24.domain.dtos.addresses.AddressCreationDto;
import com.dostavka24.dostavka24.domain.dtos.addresses.AddressUpdateDto;
import com.dostavka24.dostavka24.domain.entities.addresses.Address;
import com.dostavka24.dostavka24.service.addresses.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody AddressCreationDto addressCreationDto){
        addressService.create(addressCreationDto);
        return ResponseEntity.ok(addressCreationDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@RequestBody Long id){
        addressService.delete(id);
        return ResponseEntity.ok("deleted");
    }

    @GetMapping
    public ResponseEntity getAll(){
        List<Address> addresses = addressService.getAll();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@RequestBody Long id){
        return ResponseEntity.ok(addressService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id,@RequestBody AddressUpdateDto addressUpdateDto){
        Address updatedAddress = addressService.update(id,addressUpdateDto);
        return ResponseEntity.ok(updatedAddress);
    }
}
