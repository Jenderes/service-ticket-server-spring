package com.example.service_ticket.service.impl.ticket;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.entity.UserEntity;
import com.example.service_ticket.exception.SearchFieldNameNotFoundException;
import com.example.service_ticket.exception.TicketNotFoundException;
import com.example.service_ticket.repository.TicketRepository;
import com.example.service_ticket.service.autofill.UpdateAutoFillService;
import com.example.service_ticket.service.kafka.KafkaTicketService;
import com.example.service_ticket.service.ticket.TicketAutoFillService;
import com.example.service_ticket.service.ticket.TicketService;
import com.example.service_ticket.service.ticket.TicketValidationService;
import com.example.service_ticket.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.sample.model.Public.PUBLIC;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final UpdateAutoFillService updateAutoFillService;
    private final TicketRepository ticketRepository;
    private final TicketValidationService ticketValidationService;
    private final TicketAutoFillService ticketAutoFillService;
    private final UserService userService;
    private final KafkaTicketService kafkaTicketService;

    @Override
    public TicketEntity updateTicket(TicketEntity ticketEntity) throws TicketNotFoundException{
        Long ticketId = ticketEntity.getTicketId();
        TicketEntity oldTicket = ticketRepository.findById(ticketId);
        if (oldTicket == null)
            throw new TicketNotFoundException(ticketId);
        UserEntity userEntity = userService.getCurrentUser();
        ticketEntity.setCategory(oldTicket.getCategory());
        ticketValidationService.validateOnUpdate(ticketEntity, oldTicket);
        ticketEntity.setUpdateById(userEntity.getUserId());
        ticketEntity = updateAutoFillService.fillOnUpdate(ticketEntity, oldTicket);
        TicketEntity currentTicket = ticketRepository.update(ticketEntity);
        kafkaTicketService.sendOnUpdate(oldTicket, currentTicket);
        return currentTicket;
    }

    @Override
    public TicketEntity creatTicket(TicketEntity ticketEntity) {
        UserEntity userEntity = userService.getCurrentUser();
        ticketEntity.setCreateById(userEntity.getUserId());
        ticketEntity.setUpdateById(userEntity.getUserId());
        ticketEntity.setUserFullName(userEntity.getLastName() + " " + userEntity.getFirstName());
        ticketEntity = ticketAutoFillService.fillOnCreate(ticketEntity);
        TicketEntity currentTicket = ticketRepository.save(ticketEntity);
        kafkaTicketService.sendOnCreate(currentTicket);
        return currentTicket;
    }

    @Override
    public void deleteTicket(TicketEntity ticketEntity) {
        ticketRepository.delete(ticketEntity);
    }

    @Override
    public void deleteTicketById(Long id) throws TicketNotFoundException{
        TicketEntity deleteTicket = getTicketById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
        ticketRepository.deleteById(id);
    }

    @Override
    public List<TicketEntity> getAllTicket() {
        return ticketRepository.findAll();
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
    public List<TicketEntity> searchTicket(Map<String, String> searchParams) throws SearchFieldNameNotFoundException {
        if (searchParams.size() == 0)
            return ticketRepository.findAll();
        return ticketRepository.findTicketByCondition(getConditionByParameters(searchParams));
    }

    private static Condition getConditionByParameters(Map<String, String> conditions) throws SearchFieldNameNotFoundException {
        Map<Field<?>, String> mapCondition = new HashMap<>();
        Map<String, Field<?>> fieldNames = initialTableName();
        for (String keyCondition: conditions.keySet()){
            if (!fieldNames.containsKey(keyCondition))
                throw new SearchFieldNameNotFoundException(keyCondition);
            mapCondition.put(fieldNames.get(keyCondition), conditions.get(keyCondition));
        }
        return DSL.condition(mapCondition);
    }

    private static Map<String, Field<?>> initialTableName(){
        Map<String, Field<?>> tableNames = new HashMap<>();
        Field<?>[] fields  = PUBLIC.TICKET.fields();
        java.lang.reflect.Field[] fieldClass = TicketEntity.class.getDeclaredFields();
        String[] fieldParams = new String[fieldClass.length];
        for (int i= 0; i < fieldClass.length; i++) {
            fieldParams[i] = fieldClass[i].getName();
        }
        for (int i = 0; i < fieldParams.length; i++){
            tableNames.put(fieldParams[i], fields[i]);
        }
        return tableNames;
    }
}
