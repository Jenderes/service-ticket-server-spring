package com.example.service_ticket.service;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.entity.UserEntity;

public interface UpdateAutoFillService {

    TicketEntity fillOnUpdate(TicketEntity toUpdateTicket, TicketEntity oldTicket);

    UserEntity fillOnUpdate(UserEntity toUpdateUser, UserEntity oldUser);
}
