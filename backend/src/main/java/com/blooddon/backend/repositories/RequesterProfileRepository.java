package com.blooddon.backend.repositories;

import com.blooddon.backend.models.RequesterProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequesterProfileRepository extends JpaRepository<RequesterProfile, Long> {
}
