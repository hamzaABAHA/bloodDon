package com.blooddon.backend.services;

import com.blooddon.backend.models.RequesterProfile;
import com.blooddon.backend.repositories.RequesterProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class RequesterProfileService {

    private final RequesterProfileRepository requesterProfileRepository;

    public RequesterProfileService(RequesterProfileRepository requesterProfileRepository) {
        this.requesterProfileRepository = requesterProfileRepository;
    }

    /**
     * Finds a requester's profile by their user ID.
     * Throws an exception if the profile is not found.
     */
    public RequesterProfile getProfileByRequesterId(Long requesterId) {
        return requesterProfileRepository.findById(requesterId)
                .orElseThrow(() -> new IllegalArgumentException("Requester profile not found for ID: " + requesterId));
    }
}
