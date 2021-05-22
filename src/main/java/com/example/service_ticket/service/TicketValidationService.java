package com.example.service_ticket.service;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.model.TicketDto;

public interface TicketValidationService {
    void validateOnCreate(TicketEntity toCreateTicket);

    void validateOnUpdate(TicketEntity toUpdateTicket);
}
