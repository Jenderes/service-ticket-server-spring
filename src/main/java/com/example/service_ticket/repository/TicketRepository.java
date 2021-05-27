package com.example.service_ticket.repository;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.exception.SearchFieldNameNotFoundException;
import com.sample.model.Tables;
import com.sample.model.tables.records.TicketRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.sample.model.Public.PUBLIC;

@Repository
@RequiredArgsConstructor
public class TicketRepository implements BaseRepository<TicketEntity, Long> {

    private final DSLContext dslContext;

    @Override
    public List<TicketEntity> findAll() {
        return dslContext.selectFrom(Tables.TICKET)
                .fetch()
                .into(TicketEntity.class);
    }

    @Override
    public TicketEntity findById(Long id) {
        TicketRecord ticketRecord = dslContext.selectFrom(Tables.TICKET)
                .where(Tables.TICKET.TICKET_ID.eq(id))
                .fetchOne();
        if (ticketRecord == null)
            return null;
        return ticketRecord.into(TicketEntity.class);
    }

    @Override
    public void delete(TicketEntity entity) {
        dslContext.delete(Tables.TICKET)
                .where(Tables.TICKET.TICKET_ID.eq(entity.getTicketId()))
                .execute();
    }

    @Override
    public void deleteById(Long id) {
        dslContext.delete(Tables.TICKET)
                .where(Tables.TICKET.TICKET_ID.eq(id))
                .execute();
    }

    @Override
    public void update(TicketEntity entity) {
        dslContext.update(Tables.TICKET)
                .set(Tables.TICKET.NAME, entity.getName())
                .set(Tables.TICKET.DESCRIPTION, entity.getDescription())
                .set(Tables.TICKET.CREATE_BY_ID, entity.getCreateById())
                .set(Tables.TICKET.USER_ASSIGNEE_ID, entity.getUserAssigneeId())
                .set(Tables.TICKET.UPDATE_BY_ID, entity.getUpdateById())
                .set(Tables.TICKET.STATUS, entity.getStatus())
                .set(Tables.TICKET.CATEGORY, entity.getCategory())
                .set(Tables.TICKET.UPDATE_DATE, entity.getUpdateDate())
                .set(Tables.TICKET.CREATE_DATE, entity.getCreateDate())
                .set(Tables.TICKET.USER_FULL_NAME, entity.getUserFullName())
                .where(Tables.TICKET.TICKET_ID.eq(entity.getTicketId()))
                .execute();
    }

    @Override
    public void save(TicketEntity entity) {
        dslContext.insertInto(Tables.TICKET)
                .set(Tables.TICKET.NAME, entity.getName())
                .set(Tables.TICKET.DESCRIPTION, entity.getDescription())
                .set(Tables.TICKET.CREATE_BY_ID, entity.getCreateById())
                .set(Tables.TICKET.USER_ASSIGNEE_ID, entity.getUserAssigneeId())
                .set(Tables.TICKET.UPDATE_BY_ID, entity.getUpdateById())
                .set(Tables.TICKET.STATUS, entity.getStatus())
                .set(Tables.TICKET.CATEGORY, entity.getCategory())
                .set(Tables.TICKET.UPDATE_DATE, entity.getUpdateDate())
                .set(Tables.TICKET.CREATE_DATE, entity.getCreateDate())
                .set(Tables.TICKET.USER_FULL_NAME, entity.getUserFullName())
                .execute();
    }

    public List<TicketEntity> findTicketByAssigneeId(long assigneeId){
        return dslContext.selectFrom(Tables.TICKET)
                .where(Tables.TICKET.USER_ASSIGNEE_ID.eq(assigneeId))
                .fetch()
                .into(TicketEntity.class);
    }

    public List<TicketEntity> findTicketByUserId(long useId){
        return dslContext.selectFrom(Tables.TICKET)
                .where(Tables.TICKET.CREATE_BY_ID.eq(useId))
                .fetch()
                .into(TicketEntity.class);
    }

    public List<TicketEntity> findTicketByCategory(String category){
        return dslContext.selectFrom(Tables.TICKET)
                .where(Tables.TICKET.CATEGORY.eq(category))
                .fetch()
                .into(TicketEntity.class);
    }

    public List<TicketEntity> findTicketByParams(Map<String, String> params){
        return dslContext.selectFrom(Tables.TICKET)
                .where(condition(params))
                .fetch()
                .into(TicketEntity.class);
    }

    private static Condition condition(Map<String, String> conditions) throws SearchFieldNameNotFoundException {
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
