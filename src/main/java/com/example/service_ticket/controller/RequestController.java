package com.example.service_ticket.controller;

import com.example.service_ticket.model.MessageDto;
import com.example.service_ticket.model.RequestDto;
import com.example.service_ticket.model.WorkStatus;
import com.example.service_ticket.security.jwt.JwtProvider;
import com.example.service_ticket.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/request/")
public class RequestController {

    private final RequestService requestService;
    private final JwtProvider jwtProvider;

    public RequestController(RequestService requestService, JwtProvider jwtProvider) {
        this.requestService = requestService;
        this.jwtProvider = jwtProvider;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseEntity<?> sendTicket(HttpServletRequest request,
                                        @RequestBody RequestDto requestDto) {
        String resolveToken = jwtProvider.resolveToken(request);
        String id = jwtProvider.getUserId(resolveToken);
        requestService.createRequest(requestDto, Long.parseLong(id));
        return ResponseEntity.ok(new MessageDto("saved request"));
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public List<RequestDto> getUserRequest(HttpServletRequest request) {
        String resolveToken = jwtProvider.resolveToken(request);
        String id = jwtProvider.getUserId(resolveToken);
        return requestService.findUserRequestByUserId(Long.parseLong(id));
    }

    @RequestMapping(value = "status", method = RequestMethod.GET)
    public List<WorkStatus> getStatus(){
        return requestService.getStatusList();
    }

    @RequestMapping(value = "work", method = RequestMethod.GET)
    public List<WorkStatus> getWork(){
        return requestService.getWorkList();
    }
}
