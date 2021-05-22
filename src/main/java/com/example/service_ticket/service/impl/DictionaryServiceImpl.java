package com.example.service_ticket.service.impl;

import com.example.service_ticket.model.dictionary.DictionaryEntity;
import com.example.service_ticket.service.DictionaryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DictionaryServiceImpl<T extends DictionaryEntity> implements DictionaryService<T> {
    @Override
    public List<T> getAllValues(Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Field field = clazz.getDeclaredField("address");
            field.setAccessible(true);
            String address = (String) field.get(null);
            return objectMapper.readValue(new File("src/main/resources/json/"+address), objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
            log.info(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<T> getValueByName(Class<T> clazz, String name) {
        List<T> listValues = getAllValues(clazz);
        for (T element: listValues){
            if (element.getName().equals(name))
                return Optional.of(element);
        }
        return Optional.empty();
    }
}
