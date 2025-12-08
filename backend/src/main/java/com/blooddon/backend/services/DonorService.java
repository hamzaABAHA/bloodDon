package com.blooddon.backend.services;

import com.blooddon.backend.dto.DonationHistoryDto;
import com.blooddon.backend.models.*;
import com.blooddon.backend.repositories.BloodRequestRepository;
import com.blooddon.backend.repositories.DonorProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonorService {

    private final DonorProfileRepository donorProfileRepository;
    private final BloodRequestRepository bloodRequestRepository;

    public DonorService(DonorProfileRepository donorProfileRepository,
                        BloodRequestRepository bloodRequestRepository) {
        this.donorProfileRepository = donorProfileRepository;
        this.bloodRequestRepository = bloodRequestRepository;
    }
    // ------------------------------------------------------------------------
    // 🔍 0️⃣ Méthode de MATCHING (déjà existante dans ton projet)
    // ------------------------------------------------------------------------
    public List<DonorProfile> matchDonors(BloodType requestedBloodType,
                                          String requesterCity,
                                          boolean sameCityOnly) {

        // 1️⃣ Trouver tous les donneurs du bon groupe sanguin
        List<DonorProfile> donors = donorProfileRepository.findByBloodType(requestedBloodType);

        // 2️⃣ Filtrer par disponibilité
        donors = donors.stream()
                .filter(DonorProfile::isAvailable)
                .collect(Collectors.toList());

        // 3️⃣ Filtrer city-only
        if (sameCityOnly) {
            return donors.stream()
                    .filter(d -> d.getCity().equalsIgnoreCase(requesterCity))
                    .collect(Collectors.toList());
        }

        // 4️⃣ Sinon : trier pour mettre même ville en premier
        donors.sort(Comparator.comparing(
                (DonorProfile d) -> !d.getCity().equalsIgnoreCase(requesterCity)
        ));

        return donors;
    }


    // ------------------------------------------------------------------------
    // 1️⃣ Récupérer un donneur
    // ------------------------------------------------------------------------
    public DonorProfile getDonorById(Long id) {
        return donorProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found"));
    }

    // ------------------------------------------------------------------------
    // 2️⃣ Modifier la disponibilité (disponible / indisponible)
    // ------------------------------------------------------------------------
    public DonorProfile updateAvailability(Long id, boolean available) {
        DonorProfile donor = getDonorById(id);
        donor.setAvailable(available);
        return donorProfileRepository.save(donor);
    }

    // ------------------------------------------------------------------------
    // 3️⃣ Obtenir toutes les reviews d’un donneur
    // ------------------------------------------------------------------------
    public List<Review> getReviewsForDonor(Long donorId) {

        // On récupère TOUTES les demandes de sang
        List<BloodRequest> requests = bloodRequestRepository.findAll();

        // On extrait TOUTES les reviews liées au donorId
        return requests.stream()
                .flatMap(req -> req.getReviews().stream())
                .filter(review -> review.getDonorId().equals(donorId))
                .collect(Collectors.toList());
    }

    // ------------------------------------------------------------------------
    // 4️⃣ Calculer total de points d’un donneur
    // ------------------------------------------------------------------------
    public int getTotalPoints(Long donorId) {
        return getReviewsForDonor(donorId).stream()
                .mapToInt(Review::getPointsGiven)
                .sum();
    }

    public List<BloodRequest> getDonationHistory(Long donorId) {
        // Get all requests
        List<BloodRequest> requests = bloodRequestRepository.findAll();

        // Filter only requests where this donor is matched
        return requests.stream()
                .filter(req -> req.getMatchedDonorIds().contains(donorId))
                .collect(Collectors.toList());
    }


    public List<DonationHistoryDto> getDonationHistoryDTO(Long donorId) {
        return getDonationHistory(donorId).stream().map(req -> {

            // Get review for this donor if exists
            var review = req.getReviews().stream()
                    .filter(r -> r.getDonorId().equals(donorId))
                    .findFirst();

            return new DonationHistoryDto(
                    req.getId(),
                    req.getRequesterFullName(),
                    req.getCity(),
                    req.getBloodType().name(),
                    req.getCaseDescription(),
                    req.isCompleted(),
                    req.getCreatedAt(),
                    review.map(r -> r.getMessage()).orElse(null),
                    review.map(r -> r.getPointsGiven()).orElse(null)
            );

        }).collect(Collectors.toList());
    }

    public DonorBadge getDonorBadge(Long donorId) {

        int donations = getDonationHistory(donorId).size();

        if (donations >= 50) return DonorBadge.LEGENDARY;
        if (donations >= 20) return DonorBadge.ELITE;
        if (donations >= 10) return DonorBadge.SUPER;
        if (donations >= 5)  return DonorBadge.HERO;
        if (donations >= 3)  return DonorBadge.ACTIVE;
        if (donations >= 1)  return DonorBadge.STARTER;

        return DonorBadge.NULL;
    }


}
