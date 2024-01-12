package com.dostavka24.dostavka24.service.users;


import com.dostavka24.dostavka24.domain.dtos.users.UserCreationDto;
import com.dostavka24.dostavka24.domain.dtos.users.UserUpdateDto;
import com.dostavka24.dostavka24.domain.entities.addresses.Address;
import com.dostavka24.dostavka24.domain.entities.orders.Order;
import com.dostavka24.dostavka24.domain.entities.users.Role;
import com.dostavka24.dostavka24.domain.entities.users.User;
import com.dostavka24.dostavka24.exception.NotFoundException;
import com.dostavka24.dostavka24.repository.AddressRepository;
import com.dostavka24.dostavka24.repository.OrderRepository;
import com.dostavka24.dostavka24.repository.RoleRepository;
import com.dostavka24.dostavka24.repository.UserRepository;
import com.dostavka24.dostavka24.service.addresses.AddressService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressService addressService;
    private final OrderRepository orderRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AddressService addressService,
                       OrderRepository orderRepository,
                       RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressService = addressService;
        this.orderRepository = orderRepository;
        this.roleRepository = roleRepository;

    }


    public User create(UserCreationDto userDto){

        Role role = roleRepository.getByName(userDto.getRole());

        Order order = new Order();

        User regUser =  new User();


        regUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        regUser.setEmail(userDto.getEmail());
        regUser.setUserName(userDto.getUserName());
        regUser.setFirstName(userDto.getFirstName());
        regUser.setLastName(userDto.getLastName());
        regUser.setRole(role);
        regUser.setRegisteredAt(Instant.now());

        Address address = addressService.create(userDto.getAdress());

        regUser.setAddress(address);
        userRepository.save(regUser);

        order.setUser(regUser);
        orderRepository.save(order);

 //       emailService.sendSimpleMessage(userDto.getUserName(), "Verify Code", emailVerificationService.generateCode());

        return regUser;
    }

    public void delete(Long id){

        User user = userRepository.getById(id);
        if(user == null){
            throw new NotFoundException("User with given id not found!");
        }

        userRepository.delete(user);
    }

    public User update(Long id, UserUpdateDto updatedUserData) {

        User existingUser = userRepository.getById(id);
        if (existingUser == null) {
            throw new NotFoundException("User not found with given id " );
        }

        existingUser.setUserName(updatedUserData.getUserName());
        existingUser.setPassword(updatedUserData.getPassword());
        existingUser.setAddress(addressService.update(id, updatedUserData.getAddress()));
        existingUser.setEmail(updatedUserData.getEmail());
        existingUser.setFirstName(updatedUserData.getFirstName());
        existingUser.setLastName(updatedUserData.getLastName());
        existingUser.setRole(updatedUserData.getRole());

        return userRepository.save(existingUser);
    }

    public List<User> getAll(){

        return userRepository.findAll();
    }

    public User getById(Long id){

        User existingUser = userRepository.getById(id);
        if (existingUser == null) {
            throw new NotFoundException("User not found with this id");
        }

        return existingUser;
    }

    public Long getIdOfUser(String username){
        User existingUser = userRepository.findByUserName(username);
        if (existingUser == null) {
            throw new NotFoundException("User not found with this id");
        }
        return existingUser.getId();
    }

    public Boolean checkUserName(String userName){

        return userRepository.existsByUserName(userName);
    }


}