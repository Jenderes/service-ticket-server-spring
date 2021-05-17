package com.example.service_ticket.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
public class RequestDto {

    String header;
    String description;
    String work;
}
