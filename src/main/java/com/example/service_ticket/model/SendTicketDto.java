package com.example.service_ticket.model;

import com.example.service_ticket.entity.TicketEntity;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class SendTicketDto {
    String name;
    String description;
    String status;
    String category;
    String userFullName;

    public static SendTicketDto convertToDto(TicketEntity ticketEntity){
        return new SendTicketDto(
                ticketEntity.getName(),
                ticketEntity.getDescription(),
                ticketEntity.getStatus(),
                ticketEntity.getCategory(),
                ticketEntity.getUserFullName()
        );
    }
}
