package com.example.service_ticket.service.impl.dictionary;

import com.example.service_ticket.model.dictionary.StatusDictionaryEntity;
import com.example.service_ticket.service.dictionary.DictionaryService;
import com.example.service_ticket.service.dictionary.StatusDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusDictionaryServiceImpl extends DictionaryService<StatusDictionaryEntity> implements StatusDictionaryService {

    @Autowired
    public StatusDictionaryServiceImpl(@Value("${dictionary.status}") String path) {
        super(path, StatusDictionaryEntity.class);
    }

    @Override
    public List<StatusDictionaryEntity> getAllStatus() {
        return super.getAllValues();
    }

    @Override
    public Optional<StatusDictionaryEntity> getStatusName(String name) {
        return super.getValueByName(name);
    }
}
