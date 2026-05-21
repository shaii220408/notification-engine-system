package com.springbootapplication.notificationengine.repository;

import com.springbootapplication.notificationengine.entity.NotificationTemplate;
import com.springbootapplication.notificationengine.enums.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationTemplateRepository
        extends JpaRepository<NotificationTemplate, Long> {

    Optional<NotificationTemplate>
    findByTypeAndChannelAndActiveTrue(
            String type,
            Channel channel
    );
}