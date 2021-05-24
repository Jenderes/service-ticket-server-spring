package com.example.service_ticket.exception.dictionary;

public class StatusNotFoundException extends ValueDictionaryNotFoundException{
    public StatusNotFoundException(String name) {
        super("Not Found status by name: " + name);
    }
}
