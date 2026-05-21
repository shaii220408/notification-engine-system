package com.springbootapplication.notificationengine.repository;

import com.springbootapplication.notificationengine.entity.Notification;
import com.springbootapplication.notificationengine.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserId(String userId);

    @Query("SELECT n FROM Notification n WHERE n.status = :status")
    List<Notification> findByStatus(@Param("status") NotificationStatus status);
}