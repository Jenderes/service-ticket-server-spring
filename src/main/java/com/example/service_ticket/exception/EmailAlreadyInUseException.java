package com.example.service_ticket.exception;

public class EmailAlreadyInUseException extends RuntimeException{
    public EmailAlreadyInUseException(String email) {
        super("Пользователь уже зарегестрирован с email: " + email);
    }
}
