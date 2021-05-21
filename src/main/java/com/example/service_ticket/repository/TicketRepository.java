package com.example.service_ticket.repository;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.entity.UserEntity;
import com.sample.model.Tables;
import com.sample.model.tables.records.TicketRecord;
import com.sample.model.tables.records.UserRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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
        LocalDate dateCreate = entity.getCreateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateUpdate = entity.getUpdateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dslContext.update(Tables.TICKET)
                .set(Tables.TICKET.NAME, entity.getName())
                .set(Tables.TICKET.DESCRIPTION, entity.getDescription())
                .set(Tables.TICKET.CREATE_BY_ID, entity.getCreatBy())
                .set(Tables.TICKET.USER_ASSIGNEE_ID, entity.getUserAssignee())
                .set(Tables.TICKET.UPDATE_BY_ID, entity.getUpdateBy())
                .set(Tables.TICKET.STATUS, entity.getStatus())
                .set(Tables.TICKET.CATEGORY, entity.getCategory())
                .set(Tables.TICKET.UPDATE_DATE, dateUpdate)
                .set(Tables.TICKET.CREATE_DATE, dateCreate)
                .set(Tables.TICKET.USER_FULL_NAME, entity.getName())
                .where(Tables.TICKET.TICKET_ID.eq(entity.getTicketId()))
                .execute();
    }

    @Override
    public void save(TicketEntity entity) {
        LocalDate dateCreate = entity.getCreateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateUpdate = entity.getUpdateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dslContext.insertInto(Tables.TICKET)
                .set(Tables.TICKET.NAME, entity.getName())
                .set(Tables.TICKET.DESCRIPTION, entity.getDescription())
                .set(Tables.TICKET.CREATE_BY_ID, entity.getCreatBy())
                .set(Tables.TICKET.USER_ASSIGNEE_ID, entity.getUserAssignee())
                .set(Tables.TICKET.UPDATE_BY_ID, entity.getUpdateBy())
                .set(Tables.TICKET.STATUS, entity.getStatus())
                .set(Tables.TICKET.CATEGORY, entity.getCategory())
                .set(Tables.TICKET.UPDATE_DATE, dateUpdate)
                .set(Tables.TICKET.CREATE_DATE, dateCreate)
                .set(Tables.TICKET.USER_FULL_NAME, entity.getName())
                .execute();
    }

    public List<TicketEntity> findTicketByAssignee(long assigneeId){
        return dslContext.selectFrom(Tables.TICKET)
                .where(Tables.TICKET.USER_ASSIGNEE_ID.eq(assigneeId))
                .fetch()
                .into(TicketEntity.class);
    }

    public List<TicketEntity> findTicketByCategoryWithoutAssignee(String category){
        return dslContext.selectFrom(Tables.TICKET)
                .where(Tables.TICKET.USER_ASSIGNEE_ID.isNull())
                .and(Tables.TICKET.CATEGORY.eq(category))
                .fetch()
                .into(TicketEntity.class);
    }

    public List<TicketEntity> findTicketByUserId(long useId){
        return dslContext.selectFrom(Tables.TICKET)
                .where(Tables.TICKET.CREATE_BY_ID.eq(useId))
                .fetch()
                .into(TicketEntity.class);
    }
}
