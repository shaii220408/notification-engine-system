package com.springbootapplication.notificationengine.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PushNotificationService {

    public void sendPush(String deviceToken, String title, String body) {
        // Simulated Push - replace with Firebase when needed
        log.info("🔔 PUSH SENT TO: {} | TITLE: {} | MESSAGE: {}", deviceToken, title, body);
        log.info("✅ Push delivery simulated successfully");
    }
}