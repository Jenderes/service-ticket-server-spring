package com.example.service_ticket.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface BrokerChannel {
    @Input("tickets")
    SubscribableChannel tickets();
}
