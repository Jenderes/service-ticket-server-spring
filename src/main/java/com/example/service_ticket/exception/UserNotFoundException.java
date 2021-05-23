package com.example.service_ticket.exception;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(Long userId) {
        super("User not found by user id: " + userId);
    }
}
