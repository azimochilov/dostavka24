package com.dostavka24.dostavka24.domain.dtos.users;

import com.dostavka24.dostavka24.domain.entities.addresses.Address;
import com.dostavka24.dostavka24.domain.entities.users.Role;

import java.util.List;

public class UserCreationDto {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Address adress;
    private List<Role> roles;

    public Address getAdress() {
        return adress;
    }

    public void setAdress(Address adress) {
        this.adress = adress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
