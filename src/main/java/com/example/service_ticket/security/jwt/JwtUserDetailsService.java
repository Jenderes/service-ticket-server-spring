package com.example.service_ticket.security.jwt;

import com.example.service_ticket.entity.UserEntity;
import com.example.service_ticket.model.UserDto;
import com.example.service_ticket.service.impl.UserServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserServiceImpl userServiceImpl;

    public JwtUserDetailsService(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userServiceImpl.getUserByUsername(username).get();

        if (user == null){
            throw new UsernameNotFoundException("Employee with username: " +username+ " not found");
        }
        UserDto userDto = UserDto.convertToDto(user, userServiceImpl.getRoleByUsername(user.getUsername()));
        JwtUser jwtUser = JwtUserFactory.create(userDto);
        return JwtUserFactory.create(UserDto.convertToDto(user, userServiceImpl.getRoleByUsername(user.getUsername())));
    }
}
