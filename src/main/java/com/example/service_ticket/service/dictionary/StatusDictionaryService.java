package com.example.service_ticket.service.dictionary;

import com.example.service_ticket.model.dictionary.StatusDictionaryEntity;

import java.util.List;
import java.util.Optional;

public interface StatusDictionaryService {
    List<StatusDictionaryEntity> getAllStatus();

    Optional<StatusDictionaryEntity> getStatusName(String name);
}
