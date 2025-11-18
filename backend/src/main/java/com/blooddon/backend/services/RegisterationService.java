package com.blooddon.backend.services;

import com.blooddon.backend.dto.RegisterDonorRequest;
import com.blooddon.backend.dto.RegisterRequesterRequest;
import com.blooddon.backend.models.DonorProfile;
import com.blooddon.backend.models.RequesterProfile;
import com.blooddon.backend.models.User;
import com.blooddon.backend.repositories.DonorProfileRepository;
import com.blooddon.backend.repositories.RequesterProfileRepository;
import com.blooddon.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterationService {

    private final UserRepository userRepository;
    private final DonorProfileRepository donorProfileRepository;
    private final RequesterProfileRepository requesterProfileRepository;

    public RegisterationService(UserRepository userRepository,
                                DonorProfileRepository donorProfileRepository,
                                RequesterProfileRepository requesterProfileRepository) {
        this.userRepository = userRepository;
        this.donorProfileRepository = donorProfileRepository;
        this.requesterProfileRepository = requesterProfileRepository;
    }


    @Transactional
    public DonorProfile registerDonor(RegisterDonorRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User(
                request.getEmail(),
                request.getPassword(), // later: hash this
                User.Role.DONOR
        );

        User savedUser = userRepository.save(user);

        DonorProfile profile = new DonorProfile(
                savedUser,
                request.getFullName(),
                request.getBloodType(),
                request.getCity()
        );

        return donorProfileRepository.save(profile);
    }

    @Transactional
    public RequesterProfile registerRequester(RegisterRequesterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User(
                request.getEmail(),
                request.getPassword(),
                User.Role.REQUESTER
        );

        User savedUser = userRepository.save(user);

        RequesterProfile profile = new RequesterProfile(
                savedUser,
                request.getFullName(),
                request.getOrganizationName(),
                request.getCity(),
                request.getPhone()
        );

        return requesterProfileRepository.save(profile);
    }
}
