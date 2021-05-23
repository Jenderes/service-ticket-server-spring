package com.example.service_ticket.service.dictionary;

import com.example.service_ticket.model.dictionary.CategoryDictionaryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryDictionaryService {
    List<CategoryDictionaryEntity> getAllCategory();

    Optional<CategoryDictionaryEntity> getCategoryByName(String name);
}
