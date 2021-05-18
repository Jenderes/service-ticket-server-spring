package com.example.service_ticket.controller;

import com.example.service_ticket.model.MessageDto;
import com.example.service_ticket.model.RequestDto;
import com.example.service_ticket.security.jwt.JwtProvider;
import com.example.service_ticket.service.RequestService;
import com.example.service_ticket.service.UserService;
import com.sample.model.tables.pojos.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/manager/")
public class ManagerController {

    private final RequestService requestService;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    public ManagerController(RequestService requestService, UserService userService, JwtProvider jwtProvider) {
        this.requestService = requestService;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @RequestMapping(value = "change/{id}/{status}", method = RequestMethod.PUT)
    public ResponseEntity<?> changeStatus(@PathVariable String status, @PathVariable long id){
        if (requestService.existsRequestById(id)){
            requestService.changeStatusRequest(id, status);
            return ResponseEntity.ok(new MessageDto("Статус изменен"));
        } else {
            return ResponseEntity.badRequest().body(new MessageDto("Нет request с id: " + id));
        }
    }

    @RequestMapping(value = "get/request/work", method = RequestMethod.GET)
    public List<RequestDto> getEmptyTicket(HttpServletRequest request){
        String resolveToken = jwtProvider.resolveToken(request);
        String username = jwtProvider.getUserName(resolveToken);
        User user = userService.findUserByUserName(username);
        return requestService.findRequestsByWorkAndWithoutManager(user.getWork());
    }

    @RequestMapping(value = "get/request", method = RequestMethod.GET)
    public List<RequestDto> getManagerRequest(HttpServletRequest request){
        String resolveToken = jwtProvider.resolveToken(request);
        String id = jwtProvider.getUserId(resolveToken);
        return requestService.findRequestsByManagerId(Long.parseLong(id));
    }

}
