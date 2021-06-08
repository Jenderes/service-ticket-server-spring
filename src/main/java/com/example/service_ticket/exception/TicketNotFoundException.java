package com.example.service_ticket.exception;

public class TicketNotFoundException extends RuntimeException{
    public TicketNotFoundException(long id) {
        super("Не найден тикет с id: " + id);
    }
}
