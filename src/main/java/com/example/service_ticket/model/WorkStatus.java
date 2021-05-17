package com.example.service_ticket.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkStatus {
    private List<String> workList;
    private List<String> statusList;
}
