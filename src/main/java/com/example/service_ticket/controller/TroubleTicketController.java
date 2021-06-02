package com.example.service_ticket.controller;

import com.example.service_ticket.exception.SearchFieldNameNotFoundException;
import com.example.service_ticket.exception.TicketNotFoundException;
import com.example.service_ticket.model.ticket.TroubleTicket;
import com.example.service_ticket.service.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping(APIConstant.API + APIConstant.TICKET)
@RequiredArgsConstructor
public class TroubleTicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<?> CreateTroubleTicket(@RequestBody TroubleTicket troubleTicket){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TroubleTicket.convertToDto(ticketService.creatTicket(TroubleTicket.convertToEntity(troubleTicket))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetTroubleTicketById(@PathVariable Long id){
        return ticketService.getTicketById(id)
                .map(TroubleTicket::convertToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() ->ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> PatchTroubleTicketById(@RequestBody TroubleTicket troubleTicket, @PathVariable Long id){
        try {
             troubleTicket.setTicketId(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(TroubleTicket.convertToDto(ticketService.updateTicket(TroubleTicket.convertToEntity(troubleTicket))));
        } catch (TicketNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("не нвйде тикет по id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteTroubleTicketById(@PathVariable Long id) throws SearchFieldNameNotFoundException {
        try {
            ticketService.deleteTicketById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Удален тикет с id: " + id);
        } catch (TicketNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("не нвйде тикет по id: " + id);
        }
    }

    @GetMapping
    public ResponseEntity<?> getSpecificTroubleTicket(@RequestParam Map<String, String> params){
        try {
            return ResponseEntity.ok(ticketService.searchTicket(params)
                    .stream()
                    .map(TroubleTicket::convertToDto)
                    .collect(Collectors.toList()));
        } catch (SearchFieldNameNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
