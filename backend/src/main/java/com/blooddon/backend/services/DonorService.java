package com.blooddon.backend.services;

import com.blooddon.backend.models.BloodType;
import com.blooddon.backend.models.DonorProfile;
import com.blooddon.backend.repositories.DonorProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonorService {

    private final DonorProfileRepository donorProfileRepository;

    public DonorService(DonorProfileRepository donorProfileRepository) {
        this.donorProfileRepository = donorProfileRepository;
    }

    public List<DonorProfile> matchDonors(String requestedBloodType,
                                          String requesterCity,
                                          boolean sameCityOnly) {

        // Convert the incoming string into Enum
        BloodType type = BloodType.valueOf(requestedBloodType.toUpperCase());

        // Step 1 — Filter by blood type
        List<DonorProfile> donors = donorProfileRepository.findByBloodType(type);

        // Step 2 — Only available donors
        donors = donors.stream()
                .filter(DonorProfile::isAvailable)
                .collect(Collectors.toList());

        // Step 3 — Option A: Same city only
        if (sameCityOnly) {
            return donors.stream()
                    .filter(d -> d.getCity().equalsIgnoreCase(requesterCity))
                    .collect(Collectors.toList());
        }

        // Step 3 — Option B: All cities (sorted)
        donors.sort(Comparator.comparing(
                (DonorProfile d) -> !d.getCity().equalsIgnoreCase(requesterCity)
        ));

        return donors;
    }
}
