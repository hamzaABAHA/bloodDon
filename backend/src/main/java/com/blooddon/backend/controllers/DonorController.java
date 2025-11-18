package com.blooddon.backend.controllers;

import com.blooddon.backend.models.DonorProfile;
import com.blooddon.backend.repositories.DonorProfileRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    private final DonorProfileRepository donorProfileRepository;

    public DonorController(DonorProfileRepository donorProfileRepository) {
        this.donorProfileRepository = donorProfileRepository;
    }

    @GetMapping
    public List<DonorProfile> getAllDonors() {
        return donorProfileRepository.findAll();
    }
}
