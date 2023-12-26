package com.dostavka24.dostavka24.service.users;


import com.dostavka24.dostavka24.domain.dtos.users.UserCreationDto;
import com.dostavka24.dostavka24.domain.dtos.users.UserUpdateDto;
import com.dostavka24.dostavka24.domain.entities.orders.Order;
import com.dostavka24.dostavka24.domain.entities.users.User;
import com.dostavka24.dostavka24.exception.NotFoundException;
import com.dostavka24.dostavka24.repository.OrderRepository;
import com.dostavka24.dostavka24.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.orderRepository = orderRepository;
    }

    public User create(UserCreationDto user){

        Order order = new Order();

        User regUser =  new User();
        regUser.setPassword(passwordEncoder.encode(user.getPassword()));
        regUser.setEmail(user.getEmail());
        regUser.setUserName(user.getUserName());
        regUser.setFirstName(user.getFirstName());
        regUser.setLastName(user.getLastName());
        regUser.setRoles(user.getRoles());
        regUser.setRegisteredAt(Instant.now());
        regUser.setAddress(user.getAdress());

        userRepository.save(regUser);

        order.setUser(regUser);
        orderRepository.save(order);

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
        existingUser.setAddress(updatedUserData.getAddress());
        existingUser.setEmail(updatedUserData.getEmail());
        existingUser.setFirstName(updatedUserData.getFirstName());
        existingUser.setLastName(updatedUserData.getLastName());
        existingUser.setRoles(updatedUserData.getRoles());
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