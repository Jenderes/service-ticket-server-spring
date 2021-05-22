package com.example.service_ticket.service.impl;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.exception.StatusTransitionException;
import com.example.service_ticket.model.TicketDto;
import com.example.service_ticket.model.dictionary.CategoryTransitionDictionaryEntity;
import com.example.service_ticket.model.dictionary.StatusTransitionDictionaryEntity;
import com.example.service_ticket.repository.TicketRepository;
import com.example.service_ticket.service.DictionaryService;
import com.example.service_ticket.service.TicketService;
import com.example.service_ticket.service.TicketValidationService;
import com.example.service_ticket.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketValidationServiceImpl implements TicketValidationService {

    private final TicketRepository ticketRepository;
    private final DictionaryService<CategoryTransitionDictionaryEntity> dictionaryEntityDictionaryService;

    @Override
    public void validateOnCreate(TicketEntity toCreateTicket) {

    }

    @Override
    public void validateOnUpdate(TicketEntity toUpdateTicket) {
        Optional<CategoryTransitionDictionaryEntity> categoryTransitionDictionaryEntity= dictionaryEntityDictionaryService
                .getValueByName(CategoryTransitionDictionaryEntity.class, toUpdateTicket.getCategory());
        TicketEntity oldTicket = ticketRepository.findById(toUpdateTicket.getTicketId());
        List<StatusTransitionDictionaryEntity> listStatus = categoryTransitionDictionaryEntity
                .get()
                .getStatusTransitionDictionaryEntityList()
                .stream()
                .filter(statusTransitionDictionaryEntity ->
                        statusTransitionDictionaryEntity.getFromStatus().equals(oldTicket.getStatus())
                && statusTransitionDictionaryEntity.getToStatus().equals(toUpdateTicket.getStatus()))
                .collect(Collectors.toList());

        try {
            if (listStatus.size() == 0){
                throw new StatusTransitionException();
            }
        } catch (StatusTransitionException exception){
            log.info(exception.getMessage());
            toUpdateTicket.setStatus(oldTicket.getStatus());
        }
    }
}
