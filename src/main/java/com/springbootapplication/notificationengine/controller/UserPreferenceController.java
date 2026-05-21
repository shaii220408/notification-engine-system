package com.springbootapplication.notificationengine.controller;

import com.springbootapplication.notificationengine.entity.UserPreference;
import com.springbootapplication.notificationengine.service.UserPreferenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/preferences")
@RequiredArgsConstructor
@Tag(name = "User Preferences", description = "APIs for managing user notification preferences")
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get user preferences")
    public ResponseEntity<UserPreference> getPreferences(
            @PathVariable String userId) {
        return ResponseEntity.ok(userPreferenceService.getOrCreatePreference(userId));
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user preferences")
    public ResponseEntity<UserPreference> updatePreferences(
            @PathVariable String userId,
            @RequestParam boolean emailEnabled,
            @RequestParam boolean smsEnabled,
            @RequestParam boolean pushEnabled,
            @RequestParam String preferredChannel) {
        return ResponseEntity.ok(userPreferenceService.updatePreference(
                userId, emailEnabled, smsEnabled, pushEnabled, preferredChannel));
    }
}