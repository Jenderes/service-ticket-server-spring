package com.example.service_ticket.model.dictionary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

@Getter
public class CategoryDictionaryEntity extends DictionaryEntity {
    @JsonIgnore
    private final static String address = "category.json";

    String initialStatus;

    public CategoryDictionaryEntity(@NonNull String name, @NonNull String value, String initialStatus) {
        super(name, value);
        this.initialStatus = initialStatus;
    }

    public CategoryDictionaryEntity(){
        super();
    }
}
