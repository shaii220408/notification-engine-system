package com.springbootapplication.notificationengine.service;

import com.springbootapplication.notificationengine.dto.NotificationEvent;
import com.springbootapplication.notificationengine.entity.Notification;
import com.springbootapplication.notificationengine.entity.NotificationLog;
import com.springbootapplication.notificationengine.enums.NotificationStatus;
import com.springbootapplication.notificationengine.repository.NotificationLogRepository;
import com.springbootapplication.notificationengine.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RetryService {

    private final NotificationRepository notificationRepository;
    private final NotificationLogRepository notificationLogRepository;
    private final NotificationProducer notificationProducer;

    private static final int MAX_RETRY = 3;

    @Scheduled(fixedDelay = 120000)
    public void retryFailedNotifications() {
        log.info("Running retry job...");

        try {
            List<Notification> failedNotifications = notificationRepository
                    .findByStatus(NotificationStatus.FAILED);

            for (Notification notification : failedNotifications) {
                if (notification.getRetryCount() < MAX_RETRY) {
                    log.info("Retrying notification id: {} attempt: {}",
                            notification.getId(), notification.getRetryCount() + 1);

                    notification.setRetryCount(notification.getRetryCount() + 1);
                    notification.setStatus(NotificationStatus.PENDING);
                    notification.setNextRetryAt(LocalDateTime.now());
                    notificationRepository.save(notification);

                    NotificationEvent event = NotificationEvent.builder()
                            .notificationId(notification.getId())
                            .userId(notification.getUserId())
                            .type(notification.getType())
                            .channel(notification.getChannel())
                            .recipient(notification.getRecipient())
                            .message(notification.getMessage())
                            .build();

                    notificationProducer.sendToKafka(event);
                } else {
                    log.warn("Notification id: {} exceeded max retries.",
                            notification.getId());
                }
            }
        } catch (Exception e) {
            log.error("Retry job error: {}", e.getMessage());
        }
    }

    public void logAttempt(Long notificationId, String status, String errorMessage) {
        int attemptNumber = notificationLogRepository
                .countByNotificationId(notificationId) + 1;

        NotificationLog log2 = NotificationLog.builder()
                .notificationId(notificationId)
                .attemptNumber(attemptNumber)
                .status(status)
                .errorMessage(errorMessage)
                .build();

        notificationLogRepository.save(log2);
    }
}