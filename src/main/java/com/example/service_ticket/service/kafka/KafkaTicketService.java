package com.example.service_ticket.service.kafka;

import com.example.service_ticket.entity.TicketEntity;

public interface KafkaTicketService {
    void sendOnCreate(TicketEntity currentState);
    void sendOnUpdate(TicketEntity previousState, TicketEntity currentState);
}
