package com.blooddon.backend.controllers;

import com.blooddon.backend.dto.CreateRequestDto;
// import com.blooddon.backend.dto.MatchRequestDto; // This import is unused
import com.blooddon.backend.dto.RequestResponseDTO;
import com.blooddon.backend.dto.ReviewDto;
import com.blooddon.backend.models.DonorProfile;
import com.blooddon.backend.models.RequesterProfile;
// FIX: Remove direct repository import
// import com.blooddon.backend.repositories.RequesterProfileRepository;
import com.blooddon.backend.services.BloodRequestService;
import com.blooddon.backend.services.RequesterProfileService; // FIX: Import the new service
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requester")
public class RequestController {

    private final BloodRequestService bloodRequestService;
    // FIX: Inject the service instead of the repository
    private final RequesterProfileService requesterProfileService;

    public RequestController(BloodRequestService bloodRequestService, RequesterProfileService requesterProfileService) {
        this.bloodRequestService = bloodRequestService;
        this.requesterProfileService = requesterProfileService;
    }

    // ---------------- CREATE REQUEST ----------------
    @PostMapping("/create")
    public RequestResponseDTO createRequest(@RequestBody CreateRequestDto dto) {
        return bloodRequestService.createRequest(dto);
    }

    // ---------------- MATCH DONORS ----------------
    @GetMapping("/match/{requestId}")
    public List<DonorProfile> matchDonors(
            @PathVariable Long requestId,
            @RequestParam(defaultValue = "false") boolean sameCityOnly
    ) {
        return bloodRequestService.matchDonors(requestId, sameCityOnly);
    }

    // ---------------- ADD MATCHED DONOR ----------------
    // SUGGESTION: A more RESTful path could be "/requests/{requestId}/matched-donors"
    @PostMapping("/add-donor")
    public RequestResponseDTO addMatchedDonor(
            @RequestParam Long requestId,
            @RequestParam Long donorId
    ) {
        return bloodRequestService.addMatchedDonor(requestId, donorId);
    }

    // ---------------- ADD REVIEW ----------------
    @PostMapping("/review")
    public RequestResponseDTO addReview(@RequestBody ReviewDto dto) {
        return bloodRequestService.addReview(dto);
    }

    // ---------------- COMPLETE REQUEST ----------------
    @PostMapping("/complete/{id}")
    public RequestResponseDTO completeRequest(@PathVariable Long id) {
        return bloodRequestService.completeRequest(id);
    }

    // ---------------- GET REQUEST BY ID ----------------
    @GetMapping("/{id}")
    public RequestResponseDTO getRequest(@PathVariable Long id) {
        return bloodRequestService.getRequest(id);
    }

    // ---------------- GET REQUESTER PROFILE ----------------
    @GetMapping("/profile/{requesterId}")
    public RequesterProfile getRequesterProfile(@PathVariable Long requesterId) {
        // FIX: Use the service to fetch the profile
        return requesterProfileService.getProfileByRequesterId(requesterId);
    }
}
