package com.example.service_ticket.service.impl.autofill;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.entity.UserEntity;
import com.example.service_ticket.service.autofill.UpdateAutoFillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateAutoFillServiceImpl implements UpdateAutoFillService {

    @Override
    public TicketEntity fillOnUpdate(TicketEntity toUpdateTicket, TicketEntity oldTicket) {
        Field[] ticketField = TicketEntity.class.getDeclaredFields();
        fillObject(ticketField, toUpdateTicket, oldTicket);
        return toUpdateTicket;
    }

    @Override
    public UserEntity fillOnUpdate(UserEntity toUpdateUser, UserEntity oldUser) {
        Field[] userField = UserEntity.class.getDeclaredFields();
        fillObject(userField, toUpdateUser, oldUser);
        return toUpdateUser;
    }

    private <T> void fillObject(Field[] fields, T updateObject, T oldObject){
        for (Field field: fields){
            field.setAccessible(true);
            try {
                if (field.get(updateObject) == null)
                    field.set(updateObject, field.get(oldObject));
            } catch (IllegalAccessException e) {
                log.info(e.getMessage());
            }
        }
    }
}
