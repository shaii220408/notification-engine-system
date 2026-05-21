package com.springbootapplication.notificationengine.service;

import com.springbootapplication.notificationengine.dto.NotificationEvent;
import com.springbootapplication.notificationengine.dto.NotificationRequest;
import com.springbootapplication.notificationengine.dto.NotificationResponse;
import com.springbootapplication.notificationengine.entity.Notification;
import com.springbootapplication.notificationengine.enums.NotificationStatus;
import com.springbootapplication.notificationengine.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationProducer notificationProducer;
    private final UserPreferenceService userPreferenceService;

    public NotificationResponse sendNotification(NotificationRequest request) {

        log.info("Received notification request for userId: {}", request.getUserId());
        if (!userPreferenceService.isChannelEnabled(
                request.getUserId(), request.getChannel().name())) {
            log.warn("Channel {} is disabled for userId: {}",
                    request.getChannel(), request.getUserId());
            throw new RuntimeException("Channel " + request.getChannel()
                    + " is disabled for user " + request.getUserId());
        }

        Notification notification = Notification.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .channel(request.getChannel())
                .status(NotificationStatus.PENDING)
                .recipient(request.getRecipient())
                .message(request.getMessage())
                .build();

        Notification saved = notificationRepository.save(notification);

        NotificationEvent event = NotificationEvent.builder()
                .notificationId(saved.getId())
                .userId(saved.getUserId())
                .type(saved.getType())
                .channel(saved.getChannel())
                .recipient(saved.getRecipient())
                .message(saved.getMessage())
                .build();

        notificationProducer.sendToKafka(event);

        return mapToResponse(saved);
    }

    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<NotificationResponse> getNotificationsByUser(String userId) {
        return notificationRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private NotificationResponse mapToResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .type(notification.getType())
                .channel(notification.getChannel())
                .status(notification.getStatus())
                .recipient(notification.getRecipient())
                .message(notification.getMessage())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}