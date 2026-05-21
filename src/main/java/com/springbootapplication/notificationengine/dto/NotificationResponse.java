package com.springbootapplication.notificationengine.dto;

import com.springbootapplication.notificationengine.enums.Channel;
import com.springbootapplication.notificationengine.enums.NotificationStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponse {

    private Long id;
    private String userId;
    private String type;
    private Channel channel;
    private NotificationStatus status;
    private String recipient;
    private String message;
    private LocalDateTime createdAt;
}