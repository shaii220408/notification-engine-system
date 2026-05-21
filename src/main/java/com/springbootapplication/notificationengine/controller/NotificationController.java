package com.springbootapplication.notificationengine.controller;

import com.springbootapplication.notificationengine.dto.NotificationRequest;
import com.springbootapplication.notificationengine.dto.NotificationResponse;
import com.springbootapplication.notificationengine.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<NotificationResponse> send(
            @Valid @RequestBody NotificationRequest request) {
        return ResponseEntity.ok(notificationService.sendNotification(request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<NotificationResponse>> getAll() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getByUser(
            @PathVariable String userId) {
        return ResponseEntity.ok(notificationService.getNotificationsByUser(userId));
    }
}