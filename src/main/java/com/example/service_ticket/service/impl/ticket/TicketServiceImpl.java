package com.example.service_ticket.service.impl.ticket;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.entity.UserEntity;
import com.example.service_ticket.repository.TicketRepository;
import com.example.service_ticket.service.*;
import com.example.service_ticket.service.ticket.TicketAutoFillService;
import com.example.service_ticket.service.ticket.TicketService;
import com.example.service_ticket.service.ticket.TicketValidationService;
import com.example.service_ticket.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final UpdateAutoFillService updateAutoFillService;
    private final TicketRepository ticketRepository;
    private final TicketValidationService ticketValidationService;
    private final TicketAutoFillService ticketAutoFillService;
    private final UserService userService;

    @Override
    public void updateTicket(TicketEntity ticketEntity) {
        UserEntity userEntity = userService.getCurrentUser();
        ticketEntity.setCategory(ticketRepository.findById(ticketEntity.getTicketId()).getCategory());
        TicketEntity oldTicket = ticketRepository.findById(ticketEntity.getTicketId());
        ticketValidationService.validateOnUpdate(ticketEntity, oldTicket);
        ticketEntity.setUpdateById(userEntity.getUserId());
        ticketEntity = updateAutoFillService.fillOnUpdate(ticketEntity, oldTicket);
        ticketRepository.update(ticketEntity);
    }

    @Override
    public void creatTicket(TicketEntity ticketEntity) {
        UserEntity userEntity = userService.getCurrentUser();
        ticketEntity.setCreateById(userEntity.getUserId());
        ticketEntity.setUpdateById(userEntity.getUserId());
        ticketEntity.setUserFullName(userEntity.getLastName() + " " + userEntity.getFirstName());
        ticketEntity = ticketAutoFillService.fillOnCreate(ticketEntity);
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
