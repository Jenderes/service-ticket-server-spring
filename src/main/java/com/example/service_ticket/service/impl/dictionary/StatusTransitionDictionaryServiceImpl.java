package com.example.service_ticket.service.impl.dictionary;

import com.example.service_ticket.model.dictionary.StatusTransitionDictionaryEntity;
import com.example.service_ticket.service.dictionary.DictionaryService;
import com.example.service_ticket.service.dictionary.StatusTransitionDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusTransitionDictionaryServiceImpl extends DictionaryService<StatusTransitionDictionaryEntity> implements StatusTransitionDictionaryService {

    @Autowired
    public StatusTransitionDictionaryServiceImpl(@Value("${dictionary.status-transition}") String path) {
        super(path, StatusTransitionDictionaryEntity.class);
    }

    @Override
    public List<StatusTransitionDictionaryEntity> getAllStatusTransitions() {
        return null;
    }

    @Override
    public List<StatusTransitionDictionaryEntity> getStatusTransitionByFromStatsAndCategory(String fromStatus, String category) {
        return null;
    }
}
