package com.example.service_ticket.controller;

import com.example.service_ticket.exception.TicketNotFoundException;
import com.example.service_ticket.model.TroubleTicket;
import com.example.service_ticket.service.ticket.TicketService;
import com.sample.model.Public;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping(APIConstant.API + APIConstant.TICKET)
@RequiredArgsConstructor
public class TroubleTicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<?> CreateTroubleTicket(TroubleTicket troubleTicket, HttpServletRequest request){
        ticketService.creatTicket(TroubleTicket.convertToEntity(troubleTicket));
        return ResponseEntity.created(URI.create("ticket/"+troubleTicket.getCategory())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetTroubleTicketById(@PathVariable Long id){
        return ticketService.getTicketById(id)
                .map(TroubleTicket::convertToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() ->ResponseEntity.notFound().build());
    }
    //TODO: как правильно реализовать PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<?> PatchTroubleTicketById(TroubleTicket troubleTicket, @PathVariable Long id){
        try {
            // troubleTicket.setTicketId(id);
            // ticketService.updateTicketById(TroubleTicket.convertToEntity(troubleTicket));
            ticketService.updateTicketById(TroubleTicket.convertToEntity(troubleTicket), id);
            return ResponseEntity.noContent().build();
        } catch (TicketNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteTroubleTicketById(@PathVariable Long id){
        try {
            ticketService.deleteTicketById(id);
            return ResponseEntity.noContent().build();
        } catch (TicketNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    // TODO: Узнать правильность варианта реализации
    @GetMapping
    public ResponseEntity<?> getSpecificTroubleTicket(@RequestParam Map<String, String> params){
        if (params.containsKey("status") && params.containsKey("assigneeId"))
            return ResponseEntity.ok(ticketService.getTicketByStatusAndAssigneeId(params.get("status"),
                    Long.parseLong(params.get("assigneeId"))));
        if (params.containsKey("assigneeId") && !params.containsKey("status"))
            return ResponseEntity.ok(ticketService.getAllTicketByAssigneeId(Long.parseLong(params.get("assigneeId"))));
        if (params.containsKey("category"))
            return ResponseEntity.ok(ticketService.getAllTicketByCategory(params.get("category")));
        return ResponseEntity.ok(new ArrayList<>());
    }
}
