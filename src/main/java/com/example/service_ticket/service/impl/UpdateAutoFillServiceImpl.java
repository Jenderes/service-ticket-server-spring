package com.example.service_ticket.service.impl;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.entity.UserEntity;
import com.example.service_ticket.repository.TicketRepository;
import com.example.service_ticket.repository.UserRepository;
import com.example.service_ticket.service.UpdateAutoFillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateAutoFillServiceImpl implements UpdateAutoFillService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;


    @Override
    public TicketEntity fillOnUpdate(TicketEntity toUpdateTicket) {
        TicketEntity oldTicket = ticketRepository.findById(toUpdateTicket.getTicketId());
        Field[] ticketField = TicketEntity.class.getDeclaredFields();
        for (Field field: ticketField){
            field.setAccessible(true);
            try {
                if (field.get(toUpdateTicket) == null)
                    field.set(toUpdateTicket, field.get(oldTicket));
            } catch (IllegalAccessException e) {
                log.info(e.getMessage());
            }
        }
        return toUpdateTicket;
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
