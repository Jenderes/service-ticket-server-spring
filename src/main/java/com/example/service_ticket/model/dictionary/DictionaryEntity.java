package com.example.service_ticket.model.dictionary;

import lombok.*;


@Getter
@Setter
public abstract class DictionaryEntity {
    @NonNull
    String name;
    @NonNull
    String displayName;

    public DictionaryEntity() {
    }

    public DictionaryEntity(@NonNull String name, @NonNull String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
}
