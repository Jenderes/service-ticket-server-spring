package com.example.service_ticket.service;

import com.example.service_ticket.model.TicketDto;

public interface TicketValidationService {
    void validateOnCreate(TicketDto ticketDto);

    void validateOnUpdate(TicketDto ticketDto);
}
