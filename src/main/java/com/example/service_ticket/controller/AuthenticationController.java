package com.example.service_ticket.controller;

import com.example.service_ticket.exception.EmailAlreadyInUseException;
import com.example.service_ticket.exception.UsernameAlreadyInUserException;
import com.example.service_ticket.model.auth.AuthUserDto;
import com.example.service_ticket.model.auth.AuthenticationDto;
import com.example.service_ticket.model.auth.UserDto;
import com.example.service_ticket.security.jwt.JwtProvider;
import com.example.service_ticket.security.jwt.JwtUser;
import com.example.service_ticket.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping(APIConstant.API+APIConstant.AUTH)
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    @PostMapping("/login")
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto createUserDto) {
        try {
            userService.creatUser(UserDto.convertToEntity(createUserDto));
            return ResponseEntity.ok("Пользователь зарегистрирован");
        } catch (UsernameAlreadyInUserException | EmailAlreadyInUseException exp) {
            log.info(exp.getMessage());
            return ResponseEntity.badRequest().body(exp.getMessage());
        }
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> findUser(@PathVariable Long id){
        return userService.getUserById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
