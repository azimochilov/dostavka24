package com.dostavka24.dostavka24.rest;

import com.dostavka24.dostavka24.rest.vm.LoginVM;
import com.dostavka24.dostavka24.security.TokenProvider;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.security.auth.UserPrincipal;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserJwtController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;


    public UserJwtController(AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authoriza(@RequestBody LoginVM loginVM){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginVM.getUsername(),
                loginVM.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication,loginVM.getRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);

        // --------------------- если реализуешь UserPrincipal тогда появится
//        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof UserPrincipal) {
//            Long userId = ((UserPrincipal) principal).getUserId();
//        }
        // public class UserPrincipal implements UserDetails {----------------- вот такой класс
        //    private final UserEntity user;
        //
        //    private final Long userId;
        //    private final String username;
        //    private final String password;
        //    private final boolean isActive;
        //    private final Collection<? extends GrantedAuthority> authorities;
        //
        //    @Override
        //    public Collection<? extends GrantedAuthority> getAuthorities() {
        //        return authorities;
        //    }
        //
        //    @Override
        //    public String getPassword() {
        //        return password;
        //    }
        //
        //    @Override
        //    public String getUsername() {
        //        return username;
        //    }
        //
        //    @Override
        //    public boolean isAccountNonExpired() {
        //        return true;
        //    }
        //
        //    @Override
        //    public boolean isAccountNonLocked() {
        //        return true;
        //    }
        //
        //    @Override
        //    public boolean isCredentialsNonExpired() {
        //        return true;
        //    }
        //
        //    @Override
        //    public boolean isEnabled() {
        //        return isActive;
        //    }
        //}
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);

    }

    static class JWTToken{
        private String token;

        public JWTToken(String token) {
            this.token = token;
        }

        @JsonProperty("jwt_token")
        public String getToken() {
            return token;
        }

    }


}
