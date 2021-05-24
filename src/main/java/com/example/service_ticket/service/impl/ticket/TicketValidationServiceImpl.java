package com.example.service_ticket.service.impl.ticket;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.exception.dictionary.StatusTransitionException;
import com.example.service_ticket.model.dictionary.StatusTransitionDictionaryEntity;
import com.example.service_ticket.service.dictionary.StatusTransitionDictionaryService;
import com.example.service_ticket.service.ticket.TicketValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketValidationServiceImpl implements TicketValidationService {

    private final StatusTransitionDictionaryService statusTransitionDictionaryService;

    @Override
    public void validateOnCreate(TicketEntity toCreateTicket) {

    }

    @Override
    public void validateOnUpdate(TicketEntity toUpdateTicket, TicketEntity oldTicket) {
        List<StatusTransitionDictionaryEntity> listStatus = statusTransitionDictionaryService.getStatusTransitionByFromStatsAndCategory(oldTicket.getStatus(), oldTicket.getCategory());
        try {
            if (listStatus.parallelStream()
                    .anyMatch(statusTransitionDictionaryEntity ->
                            statusTransitionDictionaryEntity.getToStatus().equals(toUpdateTicket.getStatus()))){
                throw new StatusTransitionException();
            }
        } catch (StatusTransitionException exception){
            log.info(exception.getMessage());
            toUpdateTicket.setStatus(oldTicket.getStatus());
        }
    }
}
