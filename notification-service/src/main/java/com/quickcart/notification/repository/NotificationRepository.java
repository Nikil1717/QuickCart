package com.quickcart.notification.repository;

import com.quickcart.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<
        Notification,
        Long> {

    List<Notification>
    findByCustomerId(
            Long customerId);
}