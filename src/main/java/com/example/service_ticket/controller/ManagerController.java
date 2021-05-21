package com.example.service_ticket.controller;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.model.MessageDto;
import com.example.service_ticket.model.TicketDto;
import com.example.service_ticket.service.impl.TicketServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/manager/")
@RequiredArgsConstructor
public class ManagerController {

    private final TicketServiceImpl ticketServiceImpl;

    @RequestMapping(value = "ticket/update", method = RequestMethod.PUT)
    public ResponseEntity<?> changeStatus(@RequestBody TicketDto ticketDto){
        if (ticketServiceImpl.existsTicketById(ticketDto.getTicketId())){
            ticketServiceImpl.updateTicket(TicketDto.convertToEntity(ticketDto));
            return ResponseEntity.ok(new MessageDto("данные изменены"));
        } else {
            return ResponseEntity.badRequest().body(new MessageDto("Нет тикета с id: " + ticketDto.getTicketId()));
        }
    }

    @RequestMapping(value = "get/ticket/category", method = RequestMethod.GET)
    public List<TicketEntity> getEmptyTicket(HttpServletRequest request){
        return ticketServiceImpl.getTicketWithoutAssignee();
    }
    @RequestMapping(value = "get/ticket/assignee", method = RequestMethod.GET)
    public List<TicketEntity> getManagerRequest(HttpServletRequest request){
        return ticketServiceImpl.getTicketByAssignee();
    }
}
