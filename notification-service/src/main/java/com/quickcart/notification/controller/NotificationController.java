package com.quickcart.notification.controller;

import com.quickcart.notification.dto.NotificationResponse;
import com.quickcart.notification.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService
            notificationService;

    public NotificationController(
            NotificationService notificationService) {

        this.notificationService =
                notificationService;
    }

    @GetMapping
    public List<NotificationResponse>
    getAllNotifications() {

        return notificationService
                .getAllNotifications();
    }

    @GetMapping("/{id}")
    public NotificationResponse
    getNotification(
            @PathVariable Long id) {

        return notificationService
                .getNotification(id);
    }

    @GetMapping(
            "/customer/{customerId}")
    public List<NotificationResponse>
    getCustomerNotifications(
            @PathVariable Long customerId) {

        return notificationService
                .getCustomerNotifications(
                        customerId);
    }
}