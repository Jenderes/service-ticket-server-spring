package com.example.service_ticket.model;

import com.example.service_ticket.entity.TicketEntity;
import lombok.Value;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Value
public class TicketDto {
    Long ticketId;
    Long createTicketId;
    Long userAssigneeId;
    Long updateById;
    String name;
    String description;
    String status;
    String category;
    LocalDate createDate;
    LocalDate updateDate;
    String userFullName;

    public static TicketEntity convertToEntity(TicketDto ticketDto){
        return new TicketEntity(
                ticketDto.ticketId,
                ticketDto.createTicketId,
                ticketDto.userAssigneeId,
                ticketDto.updateById,
                ticketDto.name,
                ticketDto.description,
                ticketDto.status,
                ticketDto.category,
                ticketDto.updateDate,
                ticketDto.createDate,
                ticketDto.userFullName
        );
    }
}
