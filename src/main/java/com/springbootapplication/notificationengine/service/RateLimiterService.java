package com.springbootapplication.notificationengine.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class RateLimiterService {

    // Max 5 notifications per minute per user
    private static final int MAX_REQUESTS = 5;
    private static final int WINDOW_MINUTES = 1;

    // stores userId → list of request timestamps
    private final Map<String, java.util.List<LocalDateTime>> requestCounts
            = new ConcurrentHashMap<>();

    public boolean isAllowed(String userId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime windowStart = now.minusMinutes(WINDOW_MINUTES);

        // Get or create request list for this user
        requestCounts.putIfAbsent(userId, new java.util.ArrayList<>());
        java.util.List<LocalDateTime> requests = requestCounts.get(userId);

        // Remove requests outside the time window
        requests.removeIf(time -> time.isBefore(windowStart));

        if (requests.size() >= MAX_REQUESTS) {
            log.warn("Rate limit exceeded for userId: {}", userId);
            return false;
        }

        requests.add(now);
        return true;
    }
}