package com.example.service_ticket.service.impl;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.model.dictionary.CategoryDictionaryEntity;
import com.example.service_ticket.model.dictionary.CategoryTransitionDictionaryEntity;
import com.example.service_ticket.model.dictionary.StatusTransitionDictionaryEntity;
import com.example.service_ticket.service.DictionaryService;
import com.example.service_ticket.service.TicketAutoFillService;
import com.example.service_ticket.service.TicketService;
import com.example.service_ticket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketAutoFillServiceImpl implements TicketAutoFillService {

    private final DictionaryService<CategoryDictionaryEntity> dictionaryService;

    @Override
    public TicketEntity fillOnCreate(TicketEntity toCreateTicket) {
        Optional<CategoryDictionaryEntity> categoryDictionaryEntity= dictionaryService.getValueByName(CategoryDictionaryEntity.class, toCreateTicket.getCategory());
        toCreateTicket.setCreateDate(LocalDate.now());
        toCreateTicket.setUpdateDate(LocalDate.now());
        if (categoryDictionaryEntity.isEmpty())
            return toCreateTicket;
        toCreateTicket.setStatus(categoryDictionaryEntity.get().getInitialStatus());
        return toCreateTicket;
    }

    @Override
    public TicketEntity fillOnUpdate(TicketEntity toUpdateTicket) {
        toUpdateTicket.setUpdateDate(LocalDate.now());
        return toUpdateTicket;
    }
}
