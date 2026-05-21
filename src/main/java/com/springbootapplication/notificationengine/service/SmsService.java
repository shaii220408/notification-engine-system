package com.springbootapplication.notificationengine.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsService {

    public void sendSms(String toNumber, String body) {
        // Simulated SMS - replace with Twilio when needed
        log.info("📱 SMS SENT TO: {} | MESSAGE: {}", toNumber, body);
        log.info("✅ SMS delivery simulated successfully");
    }
}