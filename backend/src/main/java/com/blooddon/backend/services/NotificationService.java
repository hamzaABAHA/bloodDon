package com.blooddon.backend.services;

import com.blooddon.backend.models.Notification;
import com.blooddon.backend.models.User;
import com.blooddon.backend.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    public void sendNotification(User user, String title, String message) {
        repo.save(new Notification(user, title, message));
    }

    public List<Notification> getUserNotifications(User user) {
        // Call the renamed repository method
        return repo.findByUserIdOrderByCreatedAtDesc(user);
    }

    public void markAsRead(Long id) {
        Notification n = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
        n.setRead(true);
        repo.save(n);
    }
}
