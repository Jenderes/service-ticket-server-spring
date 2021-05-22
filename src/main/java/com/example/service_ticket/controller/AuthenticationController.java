package com.example.service_ticket.controller;

import com.example.service_ticket.model.AuthUserDto;
import com.example.service_ticket.model.AuthenticationDto;
import com.example.service_ticket.model.MessageDto;
import com.example.service_ticket.model.UserDto;
import com.example.service_ticket.security.jwt.JwtProvider;
import com.example.service_ticket.security.jwt.JwtUser;
import com.example.service_ticket.service.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/authentication/")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    private final UserServiceImpl userServiceImpl;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserServiceImpl userServiceImpl) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDto requestDto) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));
            JwtUser user = (JwtUser) auth.getPrincipal();
            final List<String> roles = user.getAuthorities().stream().map(
                    GrantedAuthority::getAuthority
            ).collect(Collectors.toList());
            String token = jwtProvider.createToken(user.getId(), user.getUsername(), roles);
            return ResponseEntity.ok(new AuthUserDto(
                    token, user.getId(), user.getEmail(), user.getUsername(), user.getFirstname(),
                    user.getLastname(), roles
            ));
        } catch (AuthenticationServiceException exp) {

            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserDto createUserDto) {
        try {
            ResponseEntity<?> responseEntity;
            if (userServiceImpl.existsUserByUsername(createUserDto.getUsername())){
                responseEntity = ResponseEntity.badRequest().body(new MessageDto("Пользователь с таким именен уже зарегистрирован"));
            } else if (userServiceImpl.existsUserByEmail(createUserDto.getEmail())){
                responseEntity = ResponseEntity.badRequest().body(new MessageDto("Пользователь с таким email уже зарегистрирован"));
            } else {
                userServiceImpl.creatUser(UserDto.convertToEntity(createUserDto));
                responseEntity = ResponseEntity.ok(new MessageDto("Пользователь зарегистрирован"));
            }
            return responseEntity;
        } catch (AuthenticationServiceException exp) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
