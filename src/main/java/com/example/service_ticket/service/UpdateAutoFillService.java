package com.example.service_ticket.service;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.entity.UserEntity;
import com.example.service_ticket.model.TicketDto;

public interface UpdateAutoFillService {

    TicketEntity fillOnUpdate(TicketEntity toUpdateTicket);

    UserEntity fillOnUpdate(UserEntity toUpdateUser);
}
