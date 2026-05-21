package com.springbootapplication.notificationengine.service;

import com.springbootapplication.notificationengine.dto.NotificationEvent;
import com.springbootapplication.notificationengine.enums.NotificationStatus;
import com.springbootapplication.notificationengine.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final EmailService emailService;
    private final SmsService smsService;
    private final PushNotificationService pushNotificationService;
    private final NotificationRepository notificationRepository;
    private final RetryService retryService;

    @KafkaListener(topics = "notifications.email", groupId = "notification-engine")
    public void consumeEmail(NotificationEvent event) {
        log.info("Consumed EMAIL event for userId: {}", event.getUserId());
        try {
            emailService.sendEmail(
                    event.getRecipient(),
                    "Notification: " + event.getType(),
                    event.getMessage()
            );
            updateStatus(event.getNotificationId(), NotificationStatus.SENT);
            retryService.logAttempt(event.getNotificationId(), "SENT", null);
        } catch (Exception e) {
            log.error("Failed EMAIL event: {}", e.getMessage());
            updateStatus(event.getNotificationId(), NotificationStatus.FAILED);
            retryService.logAttempt(event.getNotificationId(), "FAILED", e.getMessage());
        }
    }

    @KafkaListener(topics = "notifications.sms", groupId = "notification-engine")
    public void consumeSms(NotificationEvent event) {
        log.info("Consumed SMS event for userId: {}", event.getUserId());
        try {
            smsService.sendSms(event.getRecipient(), event.getMessage());
            updateStatus(event.getNotificationId(), NotificationStatus.SENT);
            retryService.logAttempt(event.getNotificationId(), "SENT", null);
        } catch (Exception e) {
            log.error("Failed SMS event: {}", e.getMessage());
            updateStatus(event.getNotificationId(), NotificationStatus.FAILED);
            retryService.logAttempt(event.getNotificationId(), "FAILED", e.getMessage());
        }
    }

    @KafkaListener(topics = "notifications.push", groupId = "notification-engine")
    public void consumePush(NotificationEvent event) {
        log.info("Consumed PUSH event for userId: {}", event.getUserId());
        try {
            pushNotificationService.sendPush(
                    event.getRecipient(),
                    "Notification: " + event.getType(),
                    event.getMessage()
            );
            updateStatus(event.getNotificationId(), NotificationStatus.SENT);
            retryService.logAttempt(event.getNotificationId(), "SENT", null);
        } catch (Exception e) {
            log.error("Failed PUSH event: {}", e.getMessage());
            updateStatus(event.getNotificationId(), NotificationStatus.FAILED);
            retryService.logAttempt(event.getNotificationId(), "FAILED", e.getMessage());
        }
    }

    private void updateStatus(Long id, NotificationStatus status) {
        notificationRepository.findById(id).ifPresent(notification -> {
            notification.setStatus(status);
            if (status == NotificationStatus.SENT) {
                notification.setSentAt(LocalDateTime.now());
            }
            notificationRepository.save(notification);
        });
    }
}