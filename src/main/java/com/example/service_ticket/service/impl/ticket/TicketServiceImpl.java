package com.example.service_ticket.service.impl.ticket;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.entity.UserEntity;
import com.example.service_ticket.exception.TicketNotFoundException;
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
    public void updateTicket(TicketEntity ticketEntity) throws TicketNotFoundException{
        Long ticketId = ticketEntity.getTicketId();
        TicketEntity oldTicket = getTicketById(ticketId).orElseThrow(() -> new TicketNotFoundException(ticketId));
        UserEntity userEntity = userService.getCurrentUser();
        ticketEntity.setCategory(oldTicket.getCategory());
        ticketValidationService.validateOnUpdate(ticketEntity, oldTicket);
        ticketEntity.setUpdateById(userEntity.getUserId());
        ticketEntity = updateAutoFillService.fillOnUpdate(ticketEntity, oldTicket);
        ticketRepository.update(ticketEntity);
    }

    //TODO: как правильно реализовать update ticket
//    @Override
//    public void updateTicketById(TicketEntity ticketEntity, Long id) throws TicketNotFoundException{
//        TicketEntity oldTicket = getTicketById(id).orElseThrow(() -> new TicketNotFoundException(id));
//        UserEntity userEntity = userService.getCurrentUser();
//        ticketEntity.setCategory(oldTicket.getCategory());
//        ticketValidationService.validateOnUpdate(ticketEntity, oldTicket);
//        ticketEntity.setUpdateById(userEntity.getUserId());
//        ticketEntity = updateAutoFillService.fillOnUpdate(ticketEntity, oldTicket);
//        ticketRepository.update(ticketEntity);
//    }

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
    public void deleteTicketById(Long id) throws TicketNotFoundException{
        if (getTicketById(id).isEmpty()) throw new TicketNotFoundException(id);
        ticketRepository.deleteById(id);
    }

    @Override
    public List<TicketEntity> getAllTicket() {
        return ticketRepository.findAll();
    }

    @Override
    public List<TicketEntity> getAllTicketByAssigneeId(Long assigneeId) {
        return ticketRepository.findTicketByAssigneeId(assigneeId);
    }

    @Override
    public List<TicketEntity> getAllTicketByCategory(String category) {
        return ticketRepository.findTicketByCategory(category);
    }

    @Override
    public Optional<TicketEntity> getTicketById(Long id) {
        return Optional.ofNullable(ticketRepository.findById(id));
    }

    @Override
    public List<TicketEntity> getAllTicketCurrentUser() {
        UserEntity currentUser = userService.getCurrentUser();
        return  ticketRepository.findTicketByUserId(currentUser.getUserId());
    }

    @Override
    public List<TicketEntity> getTicketByStatusAndAssigneeId(String status, Long assigneeId) {
        return  ticketRepository.findTicketByStatusAndAssigneeId(status, assigneeId);
    }


    public boolean existsTicketById(Long id){
        return getTicketById(id).isPresent();
    }

    public List<TicketEntity> getTicketUser(){
        UserEntity currentUser = userService.getCurrentUser();
        return  ticketRepository.findTicketByUserId(currentUser.getUserId());
    }
}
