package com.dostavka24.dostavka24.repository;

import com.dostavka24.dostavka24.domain.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);
    Optional<User> getUserById(Long id);
}
