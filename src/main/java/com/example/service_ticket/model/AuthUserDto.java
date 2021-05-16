package com.example.service_ticket.model;

import com.sample.model.tables.pojos.Role;

import java.util.List;

public class AuthUserDto {
    private String token;
    private long userId;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private List<String> roles;

    public AuthUserDto() {
    }

    public AuthUserDto(String token, long userId, String email, String username, String firstName, String lastName, List<String> roles) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
