package com.example.service_ticket.service;

import com.example.service_ticket.model.RequestDto;
import com.example.service_ticket.model.UserDto;
import com.example.service_ticket.model.WorkStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.model.Public;
import com.sample.model.Tables;
import com.sample.model.tables.pojos.Request;
import com.sample.model.tables.pojos.User;
import com.sample.model.tables.records.RequestRecord;
import com.sample.model.tables.records.UserRecord;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestService {

    private final DSLContext dsl;
    private final UserService userService;

    public RequestService(DSLContext dsl, UserService userService) {
        this.dsl = dsl;
        this.userService = userService;
    }

    public void createRequest(RequestDto requestDto, long id){
        User user = userService.findUserById(id);
        dsl.insertInto(Tables.REQUEST)
                .set(Tables.REQUEST.SENDER_USER_ID, id)
                .set(Tables.REQUEST.HEADER, requestDto.getHeader())
                .set(Tables.REQUEST.DESCRIPTION, requestDto.getDescription())
                .set(Tables.REQUEST.WORK, requestDto.getWork())
                .set(Tables.REQUEST.STATUS, "create")
                .set(Tables.REQUEST.USER_FULLNAME, user.getLastName() + " " + user.getFirstName())
                .execute();
    }

    public Request findRequestById(long requestId){
        RequestRecord requestRecord = dsl.selectFrom(Tables.REQUEST)
                .where(Tables.REQUEST.REQUEST_ID.eq(requestId))
                .fetchAny();
        if (requestRecord == null) {
            return null;
        }
        Request request = new Request();
        request.setRequestId(requestRecord.getRequestId());
        request.setHeader(requestRecord.getHeader());
        request.setDescription(requestRecord.getDescription());
        request.setSenderUserId(requestRecord.getSenderUserId());
        request.setAsigneeId(requestRecord.getAsigneeId());
        request.setStatus(requestRecord.getStatus());
        request.setWork(requestRecord.getWork());
        request.setUserFullname(requestRecord.getWork());
        return request;
    }

    public boolean existsRequestById(long id){
        return findRequestById(id) != null;
    }

    public void changeStatusRequest(long id, String status){
        dsl.update(Tables.REQUEST)
                .set(Tables.REQUEST.STATUS, status)
                .where(Tables.REQUEST.REQUEST_ID.eq(id))
                .execute();
    }

    public List<WorkStatus> getStatusList(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return Arrays.asList(
                    objectMapper.readValue(new File("src/main/resources/json/status.json"), WorkStatus[].class
                    ));
        } catch (IOException e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public List<WorkStatus> getWorkList(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return Arrays.asList(
                    objectMapper.readValue(new File("src/main/resources/json/work.json"), WorkStatus[].class
                    ));
        } catch (IOException e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public List<RequestDto> findRequestsByManagerId(long managerId){
        Result<RequestRecord> requestRecords = dsl.selectFrom(Tables.REQUEST)
                .where(Tables.REQUEST.ASIGNEE_ID.eq(managerId))
                .fetch();
        return requestRecords.stream()
                .map(RequestDto::convertDto)
                .collect(Collectors.toList());
    }

    public List<RequestDto> findRequestsByWorkAndWithoutManager(String work){
        Result<RequestRecord> requestRecords = dsl.selectFrom(Tables.REQUEST)
                .where(Tables.REQUEST.ASIGNEE_ID.isNull())
                .and(Tables.REQUEST.WORK.eq(work))
                .fetch();
        return requestRecords.stream()
                .map(RequestDto::convertDto)
                .collect(Collectors.toList());
    }

    public List<RequestDto> findUserRequestByUserId(long id){
        Result<RequestRecord> requestRecords = dsl.selectFrom(Tables.REQUEST)
                .where(Tables.REQUEST.SENDER_USER_ID.eq(id))
                .fetch();
        return requestRecords.stream()
                .map(RequestDto::convertDto)
                .collect(Collectors.toList());
    }
}
