package com.example.service_ticket.exception;

public class StatusTransitionException extends Exception{

    public StatusTransitionException() {
    }

    public StatusTransitionException(String message) {
        super(message);
    }
}
