package com.example.service_ticket.controller;

import com.example.service_ticket.model.MessageDto;
import com.example.service_ticket.model.RequestDto;
import com.example.service_ticket.security.jwt.JwtProvider;
import com.example.service_ticket.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final JwtProvider jwtProvider;
    private final RequestService requestService;

    public UserController(JwtProvider jwtProvider, RequestService requestService) {
        this.jwtProvider = jwtProvider;
        this.requestService = requestService;
    }

    @PostMapping("create")
    public ResponseEntity<?> sendTicket(HttpServletRequest request,
                                        @RequestBody RequestDto requestDto) {
        String resolveToken = jwtProvider.resolveToken(request);
        String id = jwtProvider.getUserId(resolveToken);
        requestService.createRequest(requestDto, Long.parseLong(id));
        return ResponseEntity.ok(new MessageDto("saved request"));
    }

}
