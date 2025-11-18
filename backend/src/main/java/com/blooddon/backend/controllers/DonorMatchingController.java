package com.blooddon.backend.controllers;

import com.blooddon.backend.models.BloodType;
import com.blooddon.backend.models.DonorProfile;
import com.blooddon.backend.services.DonorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
public class DonorMatchingController {

    public DonorMatchingController(DonorService donorMatchingService) {
        this.donorMatchingService = donorMatchingService;
    }

    private final DonorService donorMatchingService;


    @GetMapping("/match")
    public List<DonorProfile> matchDonors(
            @RequestParam String bloodType,
            @RequestParam String city,
            @RequestParam(defaultValue = "false") boolean sameCityOnly
    ) {
        return donorMatchingService.matchDonors( bloodType, city, sameCityOnly);
    }
}
