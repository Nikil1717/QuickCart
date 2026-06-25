package com.quickcart.notification.service;

import com.quickcart.notification.dto.NotificationResponse;
import com.quickcart.notification.dto.event.PaymentCompletedEvent;
import com.quickcart.notification.entity.Notification;
import com.quickcart.notification.enums.NotificationStatus;
import com.quickcart.notification.enums.NotificationType;
import com.quickcart.notification.exception.ResourceNotFoundException;
import com.quickcart.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository
            notificationRepository;

    public NotificationService(
            NotificationRepository notificationRepository) {

        this.notificationRepository =
                notificationRepository;
    }

    @Transactional
    public void sendNotification(
            PaymentCompletedEvent event) {

        Notification notification =
                new Notification();

        notification.setOrderNumber(
                event.getOrderNumber());

        notification.setCustomerId(
                event.getCustomerId());

        notification.setType(
                NotificationType.EMAIL);

        notification.setStatus(
                NotificationStatus.SENT);

        notification.setMessage(
                "Payment successful for order "
                        + event.getOrderNumber());

        notificationRepository.save(
                notification);

        System.out.println(
                "Notification sent for order : "
                        + event.getOrderNumber());
    }

    public NotificationResponse
    getNotification(
            Long id) {

        Notification notification =
                notificationRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification not found"));

        return mapToResponse(
                notification);
    }

    public List<NotificationResponse>
    getCustomerNotifications(
            Long customerId) {

        return notificationRepository
                .findByCustomerId(
                        customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<NotificationResponse>
    getAllNotifications() {

        return notificationRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private NotificationResponse
    mapToResponse(
            Notification notification) {

        NotificationResponse response =
                new NotificationResponse();

        response.setId(
                notification.getId());

        response.setOrderNumber(
                notification.getOrderNumber());

        response.setCustomerId(
                notification.getCustomerId());

        response.setMessage(
                notification.getMessage());

        response.setType(
                notification.getType()
                        .name());

        response.setStatus(
                notification.getStatus()
                        .name());

        return response;
    }
}