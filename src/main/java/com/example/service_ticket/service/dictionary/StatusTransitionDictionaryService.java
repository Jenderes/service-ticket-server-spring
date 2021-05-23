package com.example.service_ticket.service.dictionary;

import com.example.service_ticket.model.dictionary.StatusTransitionDictionaryEntity;

import java.util.List;

public interface StatusTransitionDictionaryService {
    List<StatusTransitionDictionaryEntity> getAllStatusTransitions();

    List<StatusTransitionDictionaryEntity> getStatusTransitionByFromStatsAndCategory(String fromStatus, String category);
}
