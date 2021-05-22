package com.example.service_ticket.model.dictionary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
public class StatusDictionaryEntity extends DictionaryEntity{
    @JsonIgnore
    private final static String address = "status.json";

    public StatusDictionaryEntity(@NonNull String name, @NonNull String displayName) {
        super(name, displayName);
    }

    public StatusDictionaryEntity() {
        super();
    }
}
