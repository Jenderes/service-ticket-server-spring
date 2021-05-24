package com.example.service_ticket.exception.dictionary;

public class CategoryNotFoundException extends ValueDictionaryNotFoundException{
    public CategoryNotFoundException(String name) {
        super("Not Found category by name: " + name);
    }
}
