package com.blooddon.backend.repositories;

import com.blooddon.backend.models.Notification;
import com.blooddon.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdOrderByCreatedAtDesc(User user);

}
