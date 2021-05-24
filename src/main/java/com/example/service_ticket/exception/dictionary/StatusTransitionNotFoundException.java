package com.example.service_ticket.exception.dictionary;

public class StatusTransitionNotFoundException extends ValueDictionaryNotFoundException{
    public StatusTransitionNotFoundException(String fromStatus,String category) {
        super("Not Found status transition by from status: " + fromStatus + " and category: " + category);
    }
}
