package com.springbootapplication.notificationengine.dto;

import com.springbootapplication.notificationengine.enums.Channel;
import com.springbootapplication.notificationengine.enums.NotificationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {

    private Long notificationId;

    private String userId;

    private String type;

    private Channel channel;

    private String recipient;

    private String message;

    private NotificationStatus status;

    private LocalDateTime createdAt;
}