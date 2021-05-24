package com.example.service_ticket.controller;

import com.example.service_ticket.model.AuthUserDto;
import com.example.service_ticket.model.AuthenticationDto;
import com.example.service_ticket.model.UserDto;
import com.example.service_ticket.security.jwt.JwtProvider;
import com.example.service_ticket.security.jwt.JwtUser;
import com.example.service_ticket.service.impl.user.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping(APIConstant.API+APIConstant.AUTH)
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
            return ResponseEntity.ok(AuthUserDto.convertDto(user, roles, token));
        } catch (AuthenticationServiceException exp) {
            log.info(exp.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserDto createUserDto) {
        try {
            userServiceImpl.creatUser(UserDto.convertToEntity(createUserDto));
            return ResponseEntity.ok("Пользователь зарегистрирован");
        } catch (AuthenticationServiceException exp) {
            log.info(exp.getMessage());
            return ResponseEntity.badRequest().body("Пользоваель уже зарегестрирован");
        }
    }
}
