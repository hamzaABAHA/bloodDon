package com.blooddon.backend.controllers;

import com.blooddon.backend.dto.RequesterProfileDto;
import com.blooddon.backend.mappers.RequesterProfileMapper;
import com.blooddon.backend.models.BloodRequest;
import com.blooddon.backend.models.RequesterProfile;
import com.blooddon.backend.repositories.BloodRequestRepository;
import com.blooddon.backend.repositories.RequesterProfileRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/requesters")
public class RequesterController {

    private final RequesterProfileRepository requesterProfileRepository;
    private final BloodRequestRepository bloodRequestRepository;

    public RequesterController(RequesterProfileRepository requesterProfileRepository,
                               BloodRequestRepository bloodRequestRepository) {
        this.requesterProfileRepository = requesterProfileRepository;
        this.bloodRequestRepository = bloodRequestRepository;
    }

    // ---------------------------------------------
    // GET REQUESTER PROFILE
    // ---------------------------------------------
    @GetMapping("/me/{requesterId}")
    public RequesterProfileDto getMyProfile(@PathVariable Long requesterId) {

        RequesterProfile profile = requesterProfileRepository.findById(requesterId)
                .orElseThrow(() -> new IllegalArgumentException("Requester not found"));

        return RequesterProfileMapper.toDto(profile);
    }

    // ---------------------------------------------
    // LIST ALL BLOOD REQUESTS BY THIS REQUESTER
    // ---------------------------------------------
    @GetMapping("/{requesterId}/requests")
    public List<BloodRequest> getMyRequests(@PathVariable Long requesterId) {
        return bloodRequestRepository.findByRequesterId(requesterId);
    }

    // ---------------------------------------------
    // REQUESTER DASHBOARD STATS
    // ---------------------------------------------
    @GetMapping("/{requesterId}/stats")
    public Map<String, Integer> getStats(@PathVariable Long requesterId) {

        List<BloodRequest> list = bloodRequestRepository.findByRequesterId(requesterId);

        int total = list.size();
        int completed = (int) list.stream().filter(BloodRequest::isCompleted).count();
        int pending = total - completed;

        return Map.of(
                "totalRequests", total,
                "completedRequests", completed,
                "pendingRequests", pending
        );
    }

    // ---------------------------------------------
    // MATCHED DONORS FOR A SPECIFIC REQUEST
    // ---------------------------------------------
    @GetMapping("/request/{requestId}/matched-donors")
    public List<Long> getMatchedDonors(@PathVariable Long requestId) {

        BloodRequest req = bloodRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        return req.getMatchedDonorIds();
    }

    // ---------------------------------------------
    // CLOSE / COMPLETE A REQUEST
    // ---------------------------------------------
    @PutMapping("/request/{requestId}/close")
    public BloodRequest closeRequest(@PathVariable Long requestId) {

        BloodRequest req = bloodRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        req.setCompleted(true);

        return bloodRequestRepository.save(req);
    }
}
