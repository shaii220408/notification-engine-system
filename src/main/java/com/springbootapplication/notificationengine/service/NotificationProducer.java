package com.springbootapplication.notificationengine.service;

import com.springbootapplication.notificationengine.dto.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public void sendToKafka(NotificationEvent event) {
        String topic = switch (event.getChannel()) {
            case EMAIL -> "notifications.email";
            case SMS -> "notifications.sms";
            case PUSH -> "notifications.push";
        };

        kafkaTemplate.send(topic, event.getUserId(), event);
        log.info("Event published to Kafka topic: {} for userId: {}",
                topic, event.getUserId());
    }
}