package com.springbootapplication.notificationengine.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long notificationId;

    @Column(nullable = false)
    private int attemptNumber;

    @Column(nullable = false)
    private String status;       // SENT or FAILED

    private String errorMessage;

    @Column(nullable = false)
    private LocalDateTime attemptedAt;

    @PrePersist
    public void prePersist() {
        this.attemptedAt = LocalDateTime.now();
    }
}