package com.example.service_ticket.repository;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.exception.TicketNotFoundException;
import com.sample.model.Tables;
import com.sample.model.tables.records.TicketRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.OrderField;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class TicketRepository implements BaseRepository<TicketEntity, Long> {

    private final DSLContext dslContext;

    @Override
    public List<TicketEntity> findAll() {
        return dslContext.selectFrom(Tables.TICKET)
                .orderBy(getDefaultOrderBy())
                .fetch()
                .into(TicketEntity.class);
    }

    @Override
    public TicketEntity findById(Long id) {
        TicketRecord ticketRecord = dslContext.selectFrom(Tables.TICKET)
                .where(Tables.TICKET.TICKET_ID.eq(id))
                .orderBy(getDefaultOrderBy())
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
    public TicketEntity update(TicketEntity entity) {
        return dslContext.update(Tables.TICKET)
                .set(Tables.TICKET.CREATE_BY_ID, entity.getCreateById())
                .set(Tables.TICKET.USER_ASSIGNEE_ID, entity.getUserAssigneeId())
                .set(Tables.TICKET.UPDATE_BY_ID, entity.getUpdateById())
                .set(Tables.TICKET.TICKET_INFORMATION, entity.getTicketInformation())
                .where(Tables.TICKET.TICKET_ID.eq(entity.getTicketId()))
                .returning().fetchOne().into(TicketEntity.class);
    }

    @Override
    public TicketEntity save(TicketEntity entity) {
        return dslContext.insertInto(Tables.TICKET)
                .set(Tables.TICKET.CREATE_BY_ID, entity.getCreateById())
                .set(Tables.TICKET.USER_ASSIGNEE_ID, entity.getUserAssigneeId())
                .set(Tables.TICKET.UPDATE_BY_ID, entity.getUpdateById())
                .set(Tables.TICKET.TICKET_INFORMATION, entity.getTicketInformation())
                .returning().fetchOne().into(TicketEntity.class);
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
                .orderBy(getDefaultOrderBy())
                .fetch()
                .into(TicketEntity.class);
    }

    public List<TicketEntity> findTicketByCondition(Condition condition){
        return dslContext.selectFrom(Tables.TICKET)
                .where(condition)
                .orderBy(getDefaultOrderBy())
                .fetch()
                .into(TicketEntity.class);
    }
    private OrderField<?> getDefaultOrderBy(){
        return Tables.TICKET.TICKET_ID.asc();
    }
}
