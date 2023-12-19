package com.dostavka24.dostavka24.security;

import com.dostavka24.dostavka24.domain.entities.users.Role;
import com.dostavka24.dostavka24.repository.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetail implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetail(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String lowerCase = username.toLowerCase();
        return userRepository.findByUserName(lowerCase)
                .map(user -> createSpringSecurityUser(lowerCase, user))
                .orElseThrow(() -> new UserNotActiveException("User" + username + " does not exsists in database"));
    }

    private User createSpringSecurityUser(String username, com.dostavka24.dostavka24.domain.entities.users.User user){
        if(user.isActive()){
            throw new UserNotActiveException("User" + username + " is not active");
        }

        List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new User(username, user.getPassword(), grantedAuthorities);

    }

}
