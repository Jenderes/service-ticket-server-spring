package com.example.service_ticket.controller;

import com.example.service_ticket.config.KafkaTopicConfig;
import com.example.service_ticket.model.RequestMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@CrossOrigin("*")
@RestController
@RequestMapping("api/kafka/")
@Slf4j
public class KafkaController {

    private final KafkaTemplate<String, Object> template;
    private final String topicName;
    private final int messagesPerRequest;
    private CountDownLatch latch;
    private final String idGroup;
    public KafkaController(
            final KafkaTemplate<String, Object> template,
            @Value("${kafka.topic-name}") final String topicName,
            @Value("${kafka.messages-per-request}") final int messagesPerRequest, @Value("${group.id}") final String idGroup) {
        this.template = template;
        this.topicName = topicName;
        this.messagesPerRequest = messagesPerRequest;
        this.idGroup = idGroup;
    }

    @GetMapping("/hello")
    public String hello() throws Exception {
        latch = new CountDownLatch(messagesPerRequest);
        IntStream.range(0, messagesPerRequest)
                .forEach(i -> this.template.send(topicName, String.valueOf(i),
                        new RequestMessageDto(i, "request message"))
                );
        latch.await(60, TimeUnit.SECONDS);
        log.info("All messages received");
        return "Hello Kafka!";
    }

    @KafkaListener(topics = "ticket-service", clientIdPrefix = "json",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "2")
    public void listenAsObject(ConsumerRecord<String, RequestMessageDto> cr,
                               @Payload RequestMessageDto payload) {
        log.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), payload, cr.toString());
        latch.countDown();
    }

    @KafkaListener(topics = "ticket-service", clientIdPrefix = "string",
            containerFactory = "kafkaListenerStringContainerFactory",
            groupId = "2")
    public void listenAsString(ConsumerRecord<String, String> cr,
                               @Payload String payload) {
        log.info("Logger 2 [String] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), payload, cr.toString());
        latch.countDown();
    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }
}
