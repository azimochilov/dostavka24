package com.dostavka24.dostavka24.repository;

import com.dostavka24.dostavka24.domain.entities.addresses.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByStreet(String street);
    Address getById(Long id);

}
