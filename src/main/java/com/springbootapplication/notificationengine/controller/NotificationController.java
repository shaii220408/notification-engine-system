package com.springbootapplication.notificationengine.controller;

import com.springbootapplication.notificationengine.dto.NotificationRequest;
import com.springbootapplication.notificationengine.dto.NotificationResponse;
import com.springbootapplication.notificationengine.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Engine", description = "APIs for sending and managing notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    @Operation(summary = "Send a notification", description = "Send EMAIL, SMS or PUSH notification")
    public ResponseEntity<NotificationResponse> send(
            @Valid @RequestBody NotificationRequest request) {
        return ResponseEntity.ok(notificationService.sendNotification(request));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all notifications", description = "Returns all notifications from database")
    public ResponseEntity<List<NotificationResponse>> getAll() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get notifications by user", description = "Returns all notifications for a specific user")
    public ResponseEntity<List<NotificationResponse>> getByUser(
            @PathVariable String userId) {
        return ResponseEntity.ok(notificationService.getNotificationsByUser(userId));
    }
}