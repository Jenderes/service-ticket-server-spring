package com.example.service_ticket.service.dictionary;

import com.example.service_ticket.model.dictionary.DictionaryEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class DictionaryService<T extends DictionaryEntity> {

    List<T> dictionaryValues;
    ObjectMapper objectMapper;

    public DictionaryService(String path, Class<?> entityClass) {
        objectMapper = new ObjectMapper();
        try {
            this.dictionaryValues = objectMapper.readValue(new File(path), objectMapper.getTypeFactory().constructCollectionType(List.class, entityClass));
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    public List<T> getAllValues() {
        return  dictionaryValues;
    }

    public Optional<T> getValueByName(String name) {
        return dictionaryValues.stream()
                .filter(listValue -> listValue.getName()
                .equals(name))
                .findFirst();
    }
}
