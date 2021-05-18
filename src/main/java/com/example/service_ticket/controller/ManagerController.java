package com.example.service_ticket.controller;

import com.example.service_ticket.model.MessageDto;
import com.example.service_ticket.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/manager/")
public class ManagerController {

    private final RequestService requestService;

    public ManagerController(RequestService requestService) {
        this.requestService = requestService;
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

}
