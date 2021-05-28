package com.example.service_ticket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EmailAlreadyInUseException extends RuntimeException{
    public EmailAlreadyInUseException(String email) {
        super("Пользователь уже зарегестрирован с email: " + email);
    }
}
