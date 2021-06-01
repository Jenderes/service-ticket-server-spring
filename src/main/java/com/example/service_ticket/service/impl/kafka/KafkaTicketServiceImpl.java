package com.example.service_ticket.service.impl.kafka;

import com.example.service_ticket.entity.TicketEntity;
import com.example.service_ticket.model.kafka.TicketEvent;
import com.example.service_ticket.service.kafka.KafkaTicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class KafkaTicketServiceImpl implements KafkaTicketService {
    private final KafkaTemplate<String, Object> template;
    private final String topicName;

    public KafkaTicketServiceImpl(KafkaTemplate<String, Object> template,
                                  @Value("${kafka.topic-name}") final String topicName) {
        this.template = template;
        this.topicName = topicName;
    }

    @Override
    public void sendOnCreate(TicketEntity currentState) {
        template.send(topicName, new TicketEvent(null, currentState));
    }

    @Override
    public void sendOnUpdate(TicketEntity previousState, TicketEntity currentState) {
        template.send(topicName, new TicketEvent(previousState, currentState));
    }
}
