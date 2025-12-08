package com.blooddon.backend.services;

import com.blooddon.backend.dto.CreateRequestDto;
import com.blooddon.backend.dto.RequestResponseDTO;
import com.blooddon.backend.dto.ReviewDto;
import com.blooddon.backend.mappers.BloodRequestMapper;
import com.blooddon.backend.mappers.ReviewMapper;
import com.blooddon.backend.models.BloodRequest;
import com.blooddon.backend.models.DonorProfile;
import com.blooddon.backend.models.User;
import com.blooddon.backend.repositories.BloodRequestRepository;
import com.blooddon.backend.repositories.DonorProfileRepository;
import com.blooddon.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BloodRequestService {

    private final BloodRequestRepository bloodRequestRepository;
    private final DonorProfileRepository donorProfileRepository;
    private final DonorService donorService;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public BloodRequestService(BloodRequestRepository bloodRequestRepository,
                               DonorProfileRepository donorProfileRepository,
                               DonorService donorService,
                               NotificationService notificationService,
                               UserRepository userRepository) {
        this.bloodRequestRepository = bloodRequestRepository;
        this.donorProfileRepository = donorProfileRepository;
        this.donorService = donorService;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    // ---------------- CREATE REQUEST ----------------
    @Transactional
    public RequestResponseDTO createRequest(CreateRequestDto dto) {

        User requester = userRepository.findById(dto.getRequesterId())
                .orElseThrow(() -> new RuntimeException("Requester not found"));

        BloodRequest entity = BloodRequestMapper.fromDto(dto);
        entity.setRequester(requester);

        BloodRequest saved = bloodRequestRepository.save(entity);

        List<DonorProfile> matchedDonors = donorService.matchDonors(
                dto.getBloodType(),
                dto.getCity(),
                false
        );

        List<Long> donorIds = matchedDonors.stream()
                .map(donorProfile -> donorProfile.getUser().getId())
                .collect(Collectors.toList());
        saved.setMatchedDonorIds(donorIds);

        for (DonorProfile donor : matchedDonors) {
            notificationService.sendNotification(
                    donor.getUser(),
                    "New Blood Request",
                    "A requester needs blood type: " + dto.getBloodType()
                            + " in " + dto.getCity()
            );
        }

        return BloodRequestMapper.toDto(saved);
    }


    // ---------------- FIND MATCHING DONORS ----------------
    @Transactional(readOnly = true)
    public List<DonorProfile> matchDonors(Long requestId, boolean sameCityOnly) {
        BloodRequest request = bloodRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        return donorService.matchDonors(
                request.getBloodType(),
                request.getCity(),
                sameCityOnly
        );
    }


    // ---------------- ADD MATCHED DONOR ----------------
    @Transactional
    public RequestResponseDTO addMatchedDonor(Long requestId, Long donorId) {

        BloodRequest request = bloodRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!request.getMatchedDonorIds().contains(donorId)) {
            request.getMatchedDonorIds().add(donorId);
        }

        DonorProfile donor = donorProfileRepository.findByUserId(donorId)
                .orElseThrow(() -> new RuntimeException("Donor profile not found for user ID: " + donorId));

        donor.setAvailable(false);

        return BloodRequestMapper.toDto(request);
    }


    // ---------------- ADD REVIEW ----------------
    @Transactional
    public RequestResponseDTO addReview(ReviewDto dto) {

        BloodRequest request = bloodRequestRepository.findById(dto.getRequestId())
                .orElseThrow(() -> new RuntimeException("Request not found"));

        boolean alreadyReviewed = request.getReviews().stream()
                .anyMatch(r -> r.getDonorId().equals(dto.getDonorId()));

        if (alreadyReviewed) {
            throw new IllegalArgumentException("Review already exists for this donor.");
        }

        request.getReviews().add(ReviewMapper.fromDto(dto));

        DonorProfile donorProfile = donorProfileRepository.findByUserId(dto.getDonorId())
                .orElseThrow(() -> new RuntimeException("Donor profile not found for user ID: " + dto.getDonorId()));

        notificationService.sendNotification(
                donorProfile.getUser(),
                "New Review Received",
                "Someone reviewed your donation."
        );

        return BloodRequestMapper.toDto(request);
    }


    // ---------------- COMPLETE REQUEST ----------------
    @Transactional
    public RequestResponseDTO completeRequest(Long requestId) {

        BloodRequest request = bloodRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setCompleted(true);

        notificationService.sendNotification(
                request.getRequester(),
                "Request Completed",
                "Your blood request has been marked as completed."
        );

        return BloodRequestMapper.toDto(request);
    }


    // ---------------- GET BY ID ----------------
    @Transactional(readOnly = true)
    public RequestResponseDTO getRequest(Long id) {
        BloodRequest req = bloodRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        return BloodRequestMapper.toDto(req);
    }
}
