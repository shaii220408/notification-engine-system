package com.springbootapplication.notificationengine.service;

import com.springbootapplication.notificationengine.entity.UserPreference;
import com.springbootapplication.notificationengine.repository.UserPreferenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserPreferenceService {

    private final UserPreferenceRepository userPreferenceRepository;

    public UserPreference getOrCreatePreference(String userId) {
        return userPreferenceRepository.findById(userId)
                .orElseGet(() -> {
                    UserPreference pref = UserPreference.builder()
                            .userId(userId)
                            .emailEnabled(true)
                            .smsEnabled(true)
                            .pushEnabled(true)
                            .preferredChannel("EMAIL")
                            .build();
                    return userPreferenceRepository.save(pref);
                });
    }

    public UserPreference updatePreference(String userId,
                                           boolean emailEnabled,
                                           boolean smsEnabled,
                                           boolean pushEnabled,
                                           String preferredChannel) {
        UserPreference pref = getOrCreatePreference(userId);
        pref.setEmailEnabled(emailEnabled);
        pref.setSmsEnabled(smsEnabled);
        pref.setPushEnabled(pushEnabled);
        pref.setPreferredChannel(preferredChannel);
        return userPreferenceRepository.save(pref);
    }

    public boolean isChannelEnabled(String userId, String channel) {
        UserPreference pref = getOrCreatePreference(userId);
        return switch (channel) {
            case "EMAIL" -> pref.isEmailEnabled();
            case "SMS" -> pref.isSmsEnabled();
            case "PUSH" -> pref.isPushEnabled();
            default -> true;
        };
    }
}