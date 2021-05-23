package com.example.service_ticket.exception;

public class StatusTransitionException extends RuntimeException{

    public StatusTransitionException() {
    }

    public StatusTransitionException(String message) {
        super(message);
    }
}
