package com.example.service_ticket.model.dictionary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class CategoryTransitionDictionaryEntity extends DictionaryEntity{
    @JsonIgnore
    private final static String address = "status-transition.json";

    List<StatusTransitionDictionaryEntity> statusTransitionDictionaryEntityList;

    public CategoryTransitionDictionaryEntity(@NonNull String name, @NonNull String value,
                                              List<StatusTransitionDictionaryEntity> statusTransitionDictionaryEntityList) {
        super(name, value);
        this.statusTransitionDictionaryEntityList = statusTransitionDictionaryEntityList;
    }

    public List<StatusTransitionDictionaryEntity> getStatusTransitionDictionaryEntityList() {
        return statusTransitionDictionaryEntityList;
    }

    public CategoryTransitionDictionaryEntity() {
        super();
    }
}
