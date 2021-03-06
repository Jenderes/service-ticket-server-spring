package com.example.service_ticket.service.impl.dictionary;

import com.example.service_ticket.model.dictionary.CategoryDictionaryEntity;
import com.example.service_ticket.service.dictionary.CategoryDictionaryService;
import com.example.service_ticket.service.dictionary.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryDictionaryServiceImpl extends DictionaryService<CategoryDictionaryEntity> implements CategoryDictionaryService {

    @Autowired
    public CategoryDictionaryServiceImpl(@Value("${dictionary.category}") String path) {
        super(path, CategoryDictionaryEntity.class);
    }

    @Override
    public List<CategoryDictionaryEntity> getAllCategory() {
        return super.getAllValues();
    }

    @Override
    public Optional<CategoryDictionaryEntity> getCategoryByName(String name){
        return super.getValueByName(name);
    }
}
