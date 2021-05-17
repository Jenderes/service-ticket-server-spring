package com.example.service_ticket.model;

import com.sample.model.tables.pojos.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthUserDto {
    private String token;
    private long userId;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private List<String> roles;
}
