package com.example.service_ticket.exception;

public class UsernameAlreadyInUserException extends RuntimeException{
    public UsernameAlreadyInUserException(String username) {
        super("Пользователь уже зарегестрирован с username: " + username);
    }
}
