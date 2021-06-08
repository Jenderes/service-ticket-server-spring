package com.example.service_ticket.service.impl.ticket;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.entity.TicketInformationEntity;
import com.example.service_ticket.entity.UserEntity;
import com.example.service_ticket.exception.SearchFieldNameNotFoundException;
import com.example.service_ticket.exception.TicketNotFoundException;
import com.example.service_ticket.repository.TicketRepository;
import com.example.service_ticket.service.kafka.KafkaTicketService;
import com.example.service_ticket.service.ticket.TicketAutoFillService;
import com.example.service_ticket.service.ticket.TicketService;
import com.example.service_ticket.service.ticket.TicketValidationService;
import com.example.service_ticket.service.user.UserService;
import com.example.service_ticket.utils.PatchUtils;
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
        ticketEntity.getTicketInformation().setCategory(oldTicket.getTicketInformation().getCategory());
        ticketValidationService.validateOnUpdate(ticketEntity, oldTicket);
        ticketEntity.setUpdateById(userEntity.getUserId());
        ticketEntity = ticketAutoFillService.fillOnUpdate(ticketEntity);
        ticketEntity = PatchUtils.mergeToUpdate(ticketEntity, oldTicket);
        TicketEntity currentTicket = ticketRepository.update(ticketEntity);
        kafkaTicketService.sendOnUpdate(oldTicket, currentTicket);
        return currentTicket;
    }

    @Override
    public TicketEntity creatTicket(TicketEntity ticketEntity) {
        UserEntity userEntity = userService.getCurrentUser();
        ticketEntity.setCreateById(userEntity.getUserId());
        ticketEntity.setUpdateById(userEntity.getUserId());
        ticketEntity.getTicketInformation().setUserFullName(userEntity.getLastName() + " " + userEntity.getFirstName());
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
        Condition condition = DSL.trueCondition();
        Map<String, String> fieldNames = columnsName();
        Map<String, String> fieldJsonNames = columnJsonName();
        for (String keyCondition: conditions.keySet()){
            if (fieldNames.containsKey(keyCondition)) {
                if (conditions.get(keyCondition).equals("")) {
                    condition = condition.and(ColumnStringNullCondition(fieldNames.get(keyCondition)));
                } else {
                    condition = condition.and(ColumnStringEqualsCondition(fieldNames.get(keyCondition),conditions.get(keyCondition)));
                }
            } else if (fieldJsonNames.containsKey(keyCondition)) {
                condition = condition.and(JsonStringCondition(fieldJsonNames.get(keyCondition), conditions.get(keyCondition)));
            } else {
                throw new SearchFieldNameNotFoundException(keyCondition);
            }
        }
        return condition;
    }

    private static Map<String, String> columnsName(){
        Map<String, String> tableMap = new HashMap<>();
        Field<?>[] fields  = PUBLIC.TICKET.fields();
        java.lang.reflect.Field[] fieldClass = TicketEntity.class.getDeclaredFields();
        for (int i = 0; i < fieldClass.length; i++){
            tableMap.put(fieldClass[i].getName(), fields[i].getName());
        }
        return tableMap;
    }
    private static Map<String, String> columnJsonName(){
        Map<String, String> tableJsonMap = new HashMap<>();
        java.lang.reflect.Field[] fieldClass = TicketInformationEntity.class.getDeclaredFields();
        for (java.lang.reflect.Field aClass : fieldClass) {
            tableJsonMap.put(aClass.getName(), aClass.getName());
        }
        return tableJsonMap;
    }

    private static String JsonStringCondition(String columnJsonName, String value) {
        return String.format("ticket_information @> '{\"%s\": \"%s\"}'", columnJsonName, value);
    }

    private static String ColumnStringEqualsCondition(String columnName, String value) {
        return String.format("%s = %s", columnName, value);
    }

    private static String ColumnStringNullCondition(String columnName) {
        return String.format("%s is NULL",columnName);
    }
}
