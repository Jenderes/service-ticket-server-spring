package com.example.service_ticket.service.ticket;

import com.example.service_ticket.entity.TicketEntity;

public interface TicketAutoFillService {
    TicketEntity fillOnCreate(TicketEntity toCreateTicket);

    TicketEntity fillOnUpdate(TicketEntity toUpdateTicket);
}
