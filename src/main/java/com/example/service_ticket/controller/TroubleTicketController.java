package com.example.service_ticket.controller;

import com.example.service_ticket.exception.SearchFieldNameNotFoundException;
import com.example.service_ticket.exception.TicketNotFoundException;
import com.example.service_ticket.model.TroubleTicket;
import com.example.service_ticket.service.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping(APIConstant.API + APIConstant.TICKET)
@RequiredArgsConstructor
public class TroubleTicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<?> CreateTroubleTicket(@RequestBody TroubleTicket troubleTicket){
        ticketService.creatTicket(TroubleTicket.convertToEntity(troubleTicket));
        return ResponseEntity.created(URI.create("ticket/"+troubleTicket.getCategory())).body("Ticket created");
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
    public ResponseEntity<?> PatchTroubleTicketById(@RequestBody TroubleTicket troubleTicket, @PathVariable Long id){
        try {
             troubleTicket.setTicketId(id);
             ticketService.updateTicket(TroubleTicket.convertToEntity(troubleTicket));
            return ResponseEntity.noContent().build();
        } catch (TicketNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteTroubleTicketById(@PathVariable Long id) throws SearchFieldNameNotFoundException {
        try {
            ticketService.deleteTicketById(id);
            return ResponseEntity.noContent().build();
        } catch (TicketNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    // TODO: Правильный ли варианта реализации
    @GetMapping
    public ResponseEntity<?> getSpecificTroubleTicket(@RequestParam Map<String, String> params){
        try {
            return ResponseEntity.ok(ticketService.searchTicket(params));
        } catch (SearchFieldNameNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
