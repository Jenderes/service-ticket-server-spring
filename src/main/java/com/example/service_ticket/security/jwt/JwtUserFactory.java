package com.example.service_ticket.security.jwt;

import com.example.service_ticket.entity.RoleEntity;
import com.example.service_ticket.model.UserDto;
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
                userDto.getUsername(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getStatus().equals("ACTIVE"),
                mapToGrantedAuthorities(new ArrayList<>(userDto.getRoles()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<RoleEntity> employeeRoles){
        return employeeRoles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())
        ).collect(Collectors.toList());
    }
}
