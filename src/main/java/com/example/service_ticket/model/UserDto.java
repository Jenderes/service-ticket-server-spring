package com.example.service_ticket.model;

import com.example.service_ticket.service.UserService;
import com.sample.model.tables.pojos.Role;
import com.sample.model.tables.pojos.User;

import java.util.List;

public class UserDto {
    private long userId;
    private String email;
    private String password;
    private String login;
    private String firstName;
    private String lastName;
    private List<Role> roles;
    private String status;

    public UserDto() {
    }

    public UserDto(long userId, String email, String password, String login,
                   String firstName, String lastName, List<Role> roles, String status) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
        this.status = status;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roleList) {
        this.roles = roleList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
