package com.example.service_ticket.exception;

public class SearchFieldNameNotFoundException extends RuntimeException{
    public SearchFieldNameNotFoundException(String field) {
        super("Не найдено поле с именем: " + field);
    }
}
