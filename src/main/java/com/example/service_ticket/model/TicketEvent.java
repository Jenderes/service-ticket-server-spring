package com.example.service_ticket.model;


import com.example.service_ticket.entity.TicketEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public class TicketEvent {
    String id;
    OffsetDateTime eventDate;
    TicketEntity previousState;
    TicketEntity currentState;

    public TicketEvent() {
        id = UUID.randomUUID().toString();
        this.eventDate = OffsetDateTime.now();
    }

    public TicketEvent(TicketEntity previousState, TicketEntity currentState) {
        id = UUID.randomUUID().toString();
        this.previousState = previousState;
        this.currentState = currentState;
    }
}
