package com.example.service_ticket.model;

import com.sample.model.tables.records.RequestRecord;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
public class RequestDto {
    long requestId;
    String header;
    String description;
    String work;
    String userFullName;

    public static RequestDto convertDto(RequestRecord requestRecord){
        return new RequestDto(
                requestRecord.getRequestId(),
                requestRecord.getHeader(),
                requestRecord.getDescription(),
                requestRecord.getWork(),
                requestRecord.getUserFullname());
    }
}
