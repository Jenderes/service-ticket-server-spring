package com.example.service_ticket.security.jwt;

import com.example.service_ticket.model.UserDto;
import com.sample.model.tables.pojos.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(UserDto userDto){
        return new JwtUser(
                userDto.getUserId(),
                userDto.getLogin(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getStatus().equals("active"),
                mapToGrantedAuthorities(new ArrayList<>(userDto.getRoles()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> employeeRoles){
        return employeeRoles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())
        ).collect(Collectors.toList());
    }
}
