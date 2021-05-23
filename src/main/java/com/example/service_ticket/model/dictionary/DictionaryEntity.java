package com.example.service_ticket.model.dictionary;

import lombok.*;


@Getter
@Setter
public abstract class DictionaryEntity {
    String name;
    String displayName;

    public DictionaryEntity() {

    }

}
