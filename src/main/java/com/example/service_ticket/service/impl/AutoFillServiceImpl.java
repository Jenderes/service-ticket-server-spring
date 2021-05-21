package com.example.service_ticket.service.impl;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.entity.UserEntity;
import com.example.service_ticket.model.TicketDto;
import com.example.service_ticket.repository.TicketRepository;
import com.example.service_ticket.repository.UserRepository;
import com.example.service_ticket.service.AutoFillService;
import com.example.service_ticket.service.TicketService;
import com.example.service_ticket.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoFillServiceImpl implements AutoFillService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Override
    public TicketEntity fillOnCreate(TicketEntity toCreateTicket) {
        return null;
    }

    @Override
    public TicketEntity fillOnUpdate(TicketEntity toUpdateTicket) {
        TicketEntity oldTicket = ticketRepository.findById(toUpdateTicket.getTicketId());
        Field[] ticketField = TicketEntity.class.getDeclaredFields();
        for (Field field: ticketField){
            field.setAccessible(true);
            try {
                field.set(toUpdateTicket, field.get(oldTicket));
            } catch (IllegalAccessException e) {
                log.info(e.getMessage());
            }
        }
        return toUpdateTicket;
    }

    @Override
    public UserEntity fillOnCreate(UserEntity toCreateUser) {
        return null;
    }

    @Override
    public UserEntity fillOnUpdate(UserEntity toUpdateUser) {
        UserEntity oldUser = userRepository.findById(toUpdateUser.getUserId());
        Field[] userField = UserEntity.class.getDeclaredFields();
        for (Field field: userField){
            field.setAccessible(true);
            try {
                if (field.get(toUpdateUser) == null)
                    field.set(toUpdateUser, field.get(oldUser));
            } catch (IllegalAccessException e) {
                log.info(e.getMessage());
            }
        }
        return toUpdateUser;
    }
}
