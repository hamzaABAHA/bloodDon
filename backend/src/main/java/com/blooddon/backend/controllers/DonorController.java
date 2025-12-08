package com.blooddon.backend.controllers;

import com.blooddon.backend.dto.DonationHistoryDto;
import com.blooddon.backend.models.DonorBadge;
import com.blooddon.backend.models.DonorProfile;
import com.blooddon.backend.models.Review;
import com.blooddon.backend.services.DonorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/donor")
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    // 1️⃣ Voir profil du donneur
    @GetMapping("/me/{id}")
    public DonorProfile getDonor(@PathVariable Long id) {
        return donorService.getDonorById(id);
    }

    // 2️⃣ Modifier disponibilité
    @PatchMapping("/availability/{id}")
    public DonorProfile updateAvailability(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> body
    ) {
        boolean available = body.get("available");
        return donorService.updateAvailability(id, available);
    }

    // 3️⃣ Voir reviews reçues
    @GetMapping("/reviews/{donorId}")
    public List<Review> getReviews(@PathVariable Long donorId) {
        return donorService.getReviewsForDonor(donorId);
    }

    // 4️⃣ Points reçus
    @GetMapping("/points/{donorId}")
    public int getTotalPoints(@PathVariable Long donorId) {
        return donorService.getTotalPoints(donorId);
    }
    //history of donor
    @GetMapping("/history/{donorId}")
    public List<DonationHistoryDto> getDonationHistory(@PathVariable Long donorId) {
        return donorService.getDonationHistoryDTO(donorId);
    }
    //badge
    @GetMapping("/badge/{donorId}")
    public DonorBadge getDonorBadge(@PathVariable Long donorId) {
        return donorService.getDonorBadge(donorId);
    }


}
