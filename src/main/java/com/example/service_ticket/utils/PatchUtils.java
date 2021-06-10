package com.example.service_ticket.utils;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.entity.TicketInformationEntity;
import com.example.service_ticket.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class PatchUtils {

    public static TicketEntity mergeToUpdate(TicketEntity toUpdateTicket, TicketEntity oldTicket) {
        Field[] ticketField = TicketEntity.class.getDeclaredFields();
        Field[] ticketInformationField = TicketInformationEntity.class.getDeclaredFields();
        fillObject(ticketInformationField, toUpdateTicket.getTicketInformation(), oldTicket.getTicketInformation());
        fillObject(ticketField, toUpdateTicket, oldTicket);
        return toUpdateTicket;
    }

    public static UserEntity mergeToUpdate(UserEntity toUpdateUser, UserEntity oldUser) {
        Field[] userField = UserEntity.class.getDeclaredFields();
        fillObject(userField, toUpdateUser, oldUser);
        return toUpdateUser;
    }

    private static <T> void fillObject(Field[] fields, T updateObject, T oldObject){
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
