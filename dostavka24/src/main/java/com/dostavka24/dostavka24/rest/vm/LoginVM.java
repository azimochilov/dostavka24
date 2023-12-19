package com.dostavka24.dostavka24.rest.vm;

public class LoginVM {
    private String username;
    private String password;
    private Boolean isRememberMe;

    public Boolean getRememberMe() {
        return isRememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        isRememberMe = rememberMe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
