package com.example.service_ticket.config;

import com.example.service_ticket.model.RequestMessageDto;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    private final SimpMessagingTemplate template;

    public Listener(SimpMessagingTemplate template) {
        this.template = template;
    }

    @StreamListener(target = "tickets")
    public void processMessage(RequestMessageDto requestMessageDto){
        template.convertAndSend("/topic/push", requestMessageDto);
    }
}
