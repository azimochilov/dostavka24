package com.dostavka24.dostavka24.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserPrincipalAuthToken extends AbstractAuthenticationToken {
    private final UserPrincipal principal;
    public UserPrincipalAuthToken(UserPrincipal principal) {
        super(principal.getAuthorities());
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
