package com.example.service_ticket.model;

import com.example.service_ticket.service.UserService;
import com.sample.model.tables.pojos.Role;
import com.sample.model.tables.pojos.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {
    private long userId;
    private String email;
    private String password;
    private String login;
    private String firstName;
    private String lastName;
    private List<Role> roles;
    private String status;

}
