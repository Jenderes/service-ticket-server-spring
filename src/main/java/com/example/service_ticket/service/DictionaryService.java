package com.example.service_ticket.service;

import com.example.service_ticket.model.dictionary.DictionaryEntity;

import java.util.List;
import java.util.Optional;

public interface DictionaryService<E extends DictionaryEntity> {
    List<E> getAllValues(Class<E> clazz);

    Optional<E> getValueByName(String name, Class<E> clazz);
}
