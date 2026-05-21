package com.springbootapplication.notificationengine.entity;

import com.springbootapplication.notificationengine.enums.Channel;
import com.springbootapplication.notificationengine.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Channel channel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    @Column(nullable = false)
    private String recipient;

    @Column(columnDefinition = "TEXT")
    private String message;

    private LocalDateTime scheduledAt;
    private LocalDateTime sentAt;
    private LocalDateTime createdAt;

    @Builder.Default
    @Column(nullable = false)
    private int retryCount = 0;

    private LocalDateTime nextRetryAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = NotificationStatus.PENDING;
        }
    }
}