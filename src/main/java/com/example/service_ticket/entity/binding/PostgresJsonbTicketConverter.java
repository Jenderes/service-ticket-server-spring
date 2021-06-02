package com.example.service_ticket.entity.binding;

import com.example.service_ticket.entity.TicketInformationEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Converter;
import org.jooq.JSONB;

@Slf4j
@RequiredArgsConstructor
public class PostgresJsonbTicketConverter implements Converter<JSONB, TicketInformationEntity> {

    @Override
    public TicketInformationEntity from(JSONB jsonb) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return jsonb == null ? null : objectMapper.readValue("" + jsonb, TicketInformationEntity.class);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            return null;
        }
    }

    @Override
    public JSONB to(TicketInformationEntity ticketInformationEntity) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return ticketInformationEntity == null ? null : JSONB.valueOf(objectMapper.writeValueAsString(ticketInformationEntity));
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            return null;
        }
    }

    @Override
    public Class<JSONB> fromType() {
        return JSONB.class;
    }

    @Override
    public Class<TicketInformationEntity> toType() {
        return TicketInformationEntity.class;
    }
}
