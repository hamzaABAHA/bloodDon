package com.blooddon.backend.repositories;

import com.blooddon.backend.models.BloodType;
import com.blooddon.backend.models.DonorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonorProfileRepository extends JpaRepository<DonorProfile, Long> {


    List<DonorProfile> findByBloodType(BloodType bloodType);

}


