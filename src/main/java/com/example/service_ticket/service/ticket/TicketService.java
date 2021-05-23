package com.example.service_ticket.service.ticket;

import com.example.service_ticket.entity.RoleEntity;
import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    void updateTicket(TicketEntity ticketEntity);

    void creatTicket(TicketEntity ticketEntity);

    void deleteTicket(TicketEntity ticketEntity);

    List<TicketEntity> getAllTicket();

    Optional<TicketEntity> getTicketById(Long id);

    List<TicketEntity> getTicketWithoutAssignee();

    List<TicketEntity> getTicketByAssignee();
}
