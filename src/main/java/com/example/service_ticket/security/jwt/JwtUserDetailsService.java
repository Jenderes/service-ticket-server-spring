package com.example.service_ticket.security.jwt;

import com.example.service_ticket.model.UserDto;
import com.example.service_ticket.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userService.userDtoByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException("Employee with username: " +username+ " not found");
        }
        return JwtUserFactory.create(user);
    }
}
