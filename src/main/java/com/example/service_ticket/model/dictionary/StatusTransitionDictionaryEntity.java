package com.example.service_ticket.model.dictionary;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatusTransitionDictionaryEntity extends DictionaryEntity{

    String category;
    String fromStatus;
    String toStatus;
}
