package com.example.service_ticket.model.auth;

import com.example.service_ticket.security.jwt.JwtUser;
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
    private String category;
    private List<String> roles;

    public static AuthUserDto convertDto(JwtUser jwtUser ,List<String> roles, String token){
        return new AuthUserDto(
                token,
                jwtUser.getId(),
                jwtUser.getEmail(),
                jwtUser.getUsername(),
                jwtUser.getFirstname(),
                jwtUser.getLastname(),
                jwtUser.getCategory(),
                roles
        );
    }
}

