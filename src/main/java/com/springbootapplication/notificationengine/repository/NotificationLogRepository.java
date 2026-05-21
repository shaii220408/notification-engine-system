package com.springbootapplication.notificationengine.repository;

import com.springbootapplication.notificationengine.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {

    List<NotificationLog> findByNotificationId(Long notificationId);

    int countByNotificationId(Long notificationId);
}