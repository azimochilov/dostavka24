package com.dostavka24.dostavka24.service.users;


import com.dostavka24.dostavka24.domain.entities.orders.Order;
import com.dostavka24.dostavka24.domain.entities.users.User;
import com.dostavka24.dostavka24.exception.NotFoundException;
import com.dostavka24.dostavka24.repository.OrderRepository;
import com.dostavka24.dostavka24.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
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

    public User create(User user){
        Order order = new Order();
        order.setUser(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegisteredAt(Date.from(Instant.now()));

        userRepository.save(user);
        orderRepository.save(order);

        return user;
    }

    public void delete(User user){

        userRepository.delete(user);
    }

    public User update(String userName, User updatedUserData) {
        User existingUser = userRepository.findByUserName(userName);
        if (existingUser == null) {
            throw new NotFoundException("User not found with username: " + userName);
        }

        existingUser.setUserName(updatedUserData.getUserName());
        existingUser.setPassword(updatedUserData.getPassword());
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

    public Long getId(String username){
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