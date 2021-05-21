package com.example.service_ticket.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketEntity {
    private Long   ticketId;
    private Long   creatBy;
    private Long   userAssignee;
    private Long   updateBy;
    private String name;
    private String description;
    private String status;
    private String category;
    private Date updateDate;
    private Date createDate;
    private String userFullName;
}
