package com.example.service_ticket.model.dictionary;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatusTransitionDictionaryEntity {
    String fromStatus;
    String toStatus;
}
