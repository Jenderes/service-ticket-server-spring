package com.example.service_ticket.model.dictionary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDictionaryEntity extends DictionaryEntity {

    String initialStatus;

}
