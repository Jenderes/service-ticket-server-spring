package com.example.service_ticket.controller;

import com.example.service_ticket.model.RequestDto;
import com.example.service_ticket.security.jwt.JwtProvider;
import com.example.service_ticket.service.RequestService;
import com.example.service_ticket.service.UserService;
import com.sample.model.tables.pojos.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/request/")
public class RequestController {

    private final RequestService requestService;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    public RequestController(RequestService requestService, UserService userService, JwtProvider jwtProvider) {
        this.requestService = requestService;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @RequestMapping(value = "get/manager", method = RequestMethod.GET)
    public List<RequestDto> getEmptyTicket(HttpServletRequest request){
        String resolveToken = jwtProvider.resolveToken(request);
        String username = jwtProvider.getUserName(resolveToken);
        User user = userService.findUserByUserName(username);
        return requestService.findRequestsByWorkAndWithoutManager(user.getWork());
    }

    @RequestMapping(value = "get/work", method = RequestMethod.GET)
    public List<RequestDto> getManagerRequest(HttpServletRequest request){
        String resolveToken = jwtProvider.resolveToken(request);
        String id = jwtProvider.getUserId(resolveToken);
        return requestService.findRequestsByManagerId(Long.parseLong(id));
    }

    @RequestMapping(value = "status", method = RequestMethod.GET)
    public List<String> getStatus(){
        return requestService.getStatusList();
    }

    @RequestMapping(value = "work", method = RequestMethod.GET)
    public List<String> getWork(){
        return requestService.getWorkList();
    }
}
