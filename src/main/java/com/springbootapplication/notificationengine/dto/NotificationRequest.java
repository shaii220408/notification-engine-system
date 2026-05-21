package com.springbootapplication.notificationengine.dto;

import com.springbootapplication.notificationengine.enums.Channel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Map;

@Data
public class NotificationRequest {

    @NotBlank(message = "userId is required")
    private String userId;

    @NotBlank(message = "type is required")
    private String type;

    @NotNull(message = "channel is required")
    private Channel channel;

    @NotBlank(message = "recipient is required")
    private String recipient;

    private String message;

    private Map<String, String> payload;
}