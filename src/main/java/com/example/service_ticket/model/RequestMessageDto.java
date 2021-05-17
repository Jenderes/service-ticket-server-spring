package com.example.service_ticket.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RequestMessageDto {
    long messageId;
    String message;
}
