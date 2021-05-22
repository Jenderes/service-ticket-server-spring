package com.example.service_ticket.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketEntity {
    private Long ticketId;
    private Long createById;
    private Long userAssigneeId;
    private Long updateById;
    private String name;
    private String description;
    private String status;
    private String category;
    private LocalDate updateDate;
    private LocalDate createDate;
    private String userFullName;
}
