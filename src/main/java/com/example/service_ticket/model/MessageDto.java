package com.example.service_ticket.model;

public class MessageDto {

    private String Message;

    public MessageDto(String message) {
        Message = message;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
