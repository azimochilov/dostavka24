package com.dostavka24.dostavka24.security;

import com.dostavka24.dostavka24.domain.entities.users.Privilege;
import com.dostavka24.dostavka24.domain.entities.users.Role;
import com.dostavka24.dostavka24.domain.entities.users.User;
import com.dostavka24.dostavka24.domain.entities.users.RolePrivilege;
import com.dostavka24.dostavka24.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        Role role = user.getRole();
        Collection<Privilege> rolePrivilege =
                role.getRolePrivileges().stream()
                        .map(RolePrivilege::getPrivilege).collect(Collectors.toList());
        return UserPrincipal.builder()
                .userId(user.getId())
                .username(user.getUserName())
                .password(user.getPassword())
                .isActive(user.isActive())
                .authorities(getAuthorities(rolePrivilege))
                .build();
    }
    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Privilege> privileges) {

        return getGrantedAuthorities(getPrivileges(privileges));
    }

    private List<String> getPrivileges(Collection<Privilege> privilege) {

        List<String> privileges = new ArrayList<>();
        for (Privilege item : privilege) {
            privileges.add(item.getName());

        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
