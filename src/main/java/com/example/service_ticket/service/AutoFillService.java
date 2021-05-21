package com.example.service_ticket.service;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.entity.UserEntity;
import com.example.service_ticket.model.TicketDto;

public interface AutoFillService {
    TicketEntity fillOnCreate(TicketEntity toCreateTicket);

    TicketEntity fillOnUpdate(TicketEntity toUpdateTicket);

    UserEntity fillOnCreate(UserEntity toCreateUser);

    UserEntity fillOnUpdate(UserEntity toUpdateUser);
}
