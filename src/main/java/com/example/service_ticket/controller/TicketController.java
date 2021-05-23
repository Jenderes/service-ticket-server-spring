package com.example.service_ticket.controller;

import com.example.service_ticket.model.MessageDto;
import com.example.service_ticket.model.SendTicketDto;
import com.example.service_ticket.model.TicketDto;
import com.example.service_ticket.service.dictionary.DictionaryService;
import com.example.service_ticket.service.impl.ticket.TicketServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class TicketController {
//
//    private final TicketServiceImpl ticketServiceImpl;
//    private final DictionaryService<CategoryTransitionDictionaryEntity> dictionaryService;
//
//    @RequestMapping(value = "create", method = RequestMethod.POST)
//    public ResponseEntity<?> sendTicket(HttpServletRequest request,
//                                        @RequestBody TicketDto ticketDto) {
//        ticketServiceImpl.creatTicket(TicketDto.convertToEntity(ticketDto));
//        return ResponseEntity.ok(new MessageDto("saved request"));
//    }
//
//    @RequestMapping(value = "update", method = RequestMethod.PUT)
//    public ResponseEntity<?> changeStatus(@RequestBody TicketDto ticketDto){
//        if (ticketServiceImpl.existsTicketById(ticketDto.getTicketId())){
//            ticketServiceImpl.updateTicket(TicketDto.convertToEntity(ticketDto));
//            return ResponseEntity.ok(new MessageDto("данные изменены"));
//        } else {
//            return ResponseEntity.badRequest().body(new MessageDto("Нет тикета с id: " + ticketDto.getTicketId()));
//        }
//    }
//
//    @RequestMapping(value = "get", method = RequestMethod.GET)
//    public List<SendTicketDto> getUserRequest(HttpServletRequest request) {
//        return ticketServiceImpl.getTicketUser()
//                .stream()
//                .map(SendTicketDto::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    @RequestMapping(value = "dictionary", method = RequestMethod.GET)
//    public List<CategoryTransitionDictionaryEntity> getWork(){
//        return dictionaryService.getAllValues(CategoryTransitionDictionaryEntity.class);
//    }
}
