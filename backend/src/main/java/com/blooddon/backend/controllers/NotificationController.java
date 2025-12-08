package com.blooddon.backend.controllers;

import com.blooddon.backend.models.Notification;
import com.blooddon.backend.models.User; // Import User
import com.blooddon.backend.repositories.UserRepository; // Import UserRepository
import com.blooddon.backend.services.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;
    private final UserRepository userRepository; // Inject UserRepository

    public NotificationController(NotificationService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}")
    public List<Notification> getNotifications(@PathVariable Long userId) {
        // 1. Find the user by their ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // 2. Pass the User object to the service
        return service.getUserNotifications(user);
    }

    @PutMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        service.markAsRead(id);
    }
}
