package com.example.service_ticket.service.ticket;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.exception.TicketNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TicketService {

    TicketEntity updateTicket(TicketEntity ticketEntity) throws TicketNotFoundException;

    TicketEntity creatTicket(TicketEntity ticketEntity);

    void deleteTicket(TicketEntity ticketEntity);

    void deleteTicketById(Long id);

    List<TicketEntity> getAllTicket();

    Optional<TicketEntity> getTicketById(Long id);

    List<TicketEntity> getAllTicketCurrentUser();

    List<TicketEntity> searchTicket(Map<String, String> searchParams);

}
