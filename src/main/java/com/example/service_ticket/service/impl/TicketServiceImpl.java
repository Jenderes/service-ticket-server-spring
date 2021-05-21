package com.example.service_ticket.service.impl;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.entity.UserEntity;
import com.example.service_ticket.repository.TicketRepository;
import com.example.service_ticket.service.AutoFillService;
import com.example.service_ticket.service.TicketService;
import com.example.service_ticket.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final AutoFillService autoFillService;
    private final TicketRepository ticketRepository;
    private final UserService userService;

    @Override
    public void updateTicket(TicketEntity ticketEntity) {
        ticketEntity = autoFillService.fillOnUpdate(ticketEntity);
        ticketRepository.update(ticketEntity);
    }

    @Override
    public void creatTicket(TicketEntity ticketEntity) {
        ticketRepository.save(ticketEntity);
    }

    @Override
    public void deleteTicket(TicketEntity ticketEntity) {
        ticketRepository.delete(ticketEntity);
    }

    @Override
    public List<TicketEntity> getAllTicket() {
        return ticketRepository.findAll();
    }

    @Override
    public Optional<TicketEntity> getTicketById(Long id) {
        return Optional.of(ticketRepository.findById(id));
    }

    @Override
    public List<TicketEntity> getTicketWithoutAssignee() {
        UserEntity currentUser = userService.getCurrentUser();
        return  ticketRepository.findTicketByCategoryWithoutAssignee(currentUser.getCategory());
    }

    @Override
    public List<TicketEntity> getTicketByAssignee() {
        UserEntity currentUser = userService.getCurrentUser();
        return  ticketRepository.findTicketByAssignee(currentUser.getUserId());
    }

    public boolean existsTicketById(Long id){
        return getTicketById(id).isPresent();
    }

    public List<TicketEntity> getTicketUser(){
        UserEntity currentUser = userService.getCurrentUser();
        return  ticketRepository.findTicketByUserId(currentUser.getUserId());
    }
}
