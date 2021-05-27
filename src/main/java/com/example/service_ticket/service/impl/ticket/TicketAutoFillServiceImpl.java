package com.example.service_ticket.service.impl.ticket;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.service.dictionary.CategoryDictionaryService;
import com.example.service_ticket.service.ticket.TicketAutoFillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TicketAutoFillServiceImpl implements TicketAutoFillService {

    private final CategoryDictionaryService dictionaryService;

    @Override
    public TicketEntity fillOnCreate(TicketEntity toCreateTicket) {
        toCreateTicket.setCreateDate(LocalDate.now());
        toCreateTicket.setUpdateDate(LocalDate.now());
        dictionaryService.getCategoryByName(toCreateTicket.getCategory())
                .ifPresent((categoryDictionaryEntity1) ->
                        toCreateTicket.setStatus(categoryDictionaryEntity1.getInitialStatus()));
        return toCreateTicket;
    }

    @Override
    public TicketEntity fillOnUpdate(TicketEntity toUpdateTicket) {
        toUpdateTicket.setUpdateDate(LocalDate.now());
        return toUpdateTicket;
    }
}
