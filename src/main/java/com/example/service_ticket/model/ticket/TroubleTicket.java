package com.example.service_ticket.model.ticket;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.entity.TicketInformationEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TroubleTicket {
    Long ticketId;
    Long createById;
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
                troubleTicket.createById,
                troubleTicket.userAssigneeId,
                troubleTicket.updateById,
                new TicketInformationEntity(
                        troubleTicket.name,
                        troubleTicket.description,
                        troubleTicket.category,
                        troubleTicket.status,
                        troubleTicket.userFullName,
                        troubleTicket.createDate,
                        troubleTicket.updateDate)
        );
    }
    public static TroubleTicket convertToDto(TicketEntity ticketEntity){
        return new TroubleTicket(
                ticketEntity.getTicketId(),
                ticketEntity.getCreateById(),
                ticketEntity.getUserAssigneeId(),
                ticketEntity.getUpdateById(),
                ticketEntity.getTicketInformation().getName(),
                ticketEntity.getTicketInformation().getDescription(),
                ticketEntity.getTicketInformation().getStatus(),
                ticketEntity.getTicketInformation().getCategory(),
                ticketEntity.getTicketInformation().getUpdateDate(),
                ticketEntity.getTicketInformation().getCreateDate(),
                ticketEntity.getTicketInformation().getUserFullName()
        );
    }
}
