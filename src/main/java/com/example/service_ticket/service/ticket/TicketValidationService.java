package com.example.service_ticket.service.ticket;

import com.example.service_ticket.entity.TicketEntity;

public interface TicketValidationService {
    void validateOnCreate(TicketEntity toCreateTicket);

    void validateOnUpdate(TicketEntity toUpdateTicket, TicketEntity oldTicket);
}
