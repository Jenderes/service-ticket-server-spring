package com.example.service_ticket.model;

import com.example.service_ticket.entity.TicketEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TroubleTicket {
    Long ticketId;
    Long createBytId;
    Long userAssigneeId;
    Long updateById;
    String name;
    String description;
    String status;
    String category;
    LocalDate createDate;
    LocalDate updateDate;
    String userFullName;

    public static TicketEntity convertToEntity(TroubleTicket troubleTicket){
        return new TicketEntity(
                troubleTicket.ticketId,
                troubleTicket.createBytId,
                troubleTicket.userAssigneeId,
                troubleTicket.updateById,
                troubleTicket.name,
                troubleTicket.description,
                troubleTicket.status,
                troubleTicket.category,
                troubleTicket.updateDate,
                troubleTicket.createDate,
                troubleTicket.userFullName
        );
    }
    public static TroubleTicket convertToDto(TicketEntity ticketEntity){
        return new TroubleTicket(
                ticketEntity.getTicketId(),
                ticketEntity.getCreateById(),
                ticketEntity.getUserAssigneeId(),
                ticketEntity.getUpdateById(),
                ticketEntity.getName(),
                ticketEntity.getDescription(),
                ticketEntity.getStatus(),
                ticketEntity.getCategory(),
                ticketEntity.getUpdateDate(),
                ticketEntity.getCreateDate(),
                ticketEntity.getUserFullName()
        );
    }
}
