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
        toCreateTicket.getTicketInformation().setCreateDate(LocalDate.now());
        toCreateTicket.getTicketInformation().setUpdateDate(LocalDate.now());
        dictionaryService.getCategoryByName(toCreateTicket.getTicketInformation().getCategory())
                .ifPresent((categoryDictionaryEntity1) ->
                        toCreateTicket.getTicketInformation().setStatus(categoryDictionaryEntity1.getInitialStatus()));
        return toCreateTicket;
    }

    @Override
    public TicketEntity fillOnUpdate(TicketEntity toUpdateTicket) {
        toUpdateTicket.getTicketInformation().setUpdateDate(LocalDate.now());
        return toUpdateTicket;
    }
}
