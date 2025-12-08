package com.blooddon.backend.services;

import com.blooddon.backend.dto.RegisterDonorRequest;
import com.blooddon.backend.dto.RegisterRequesterRequest;
import com.blooddon.backend.mappers.DonorMapper;
import com.blooddon.backend.mappers.RequesterRegistrationMapper;
import com.blooddon.backend.models.DonorProfile;
import com.blooddon.backend.models.RequesterProfile;
import com.blooddon.backend.models.User;
import com.blooddon.backend.repositories.DonorProfileRepository;
import com.blooddon.backend.repositories.RequesterProfileRepository;
import com.blooddon.backend.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterationService {

    private final UserRepository userRepository;
    private final DonorProfileRepository donorProfileRepository;
    private final RequesterProfileRepository requesterProfileRepository;
    private final PasswordEncoder passwordEncoder;


    public RegisterationService(UserRepository userRepository,
                                DonorProfileRepository donorProfileRepository,
                                RequesterProfileRepository requesterProfileRepository,
                                PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.donorProfileRepository = donorProfileRepository;
        this.requesterProfileRepository = requesterProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // ---------------- REGISTER DONOR ----------------
    @Transactional
    public DonorProfile registerDonor(RegisterDonorRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Create user entity
        User user = DonorMapper.toUser(request,passwordEncoder);
        User savedUser = userRepository.save(user);

        // Create donor profile
        DonorProfile profile = DonorMapper.toDonorProfile(request, savedUser);

        return donorProfileRepository.save(profile);
    }

    // ---------------- REGISTER REQUESTER ----------------
    @Transactional
    public RequesterProfile registerRequester(RegisterRequesterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Create user entity
        User user = RequesterRegistrationMapper.toUser(request,passwordEncoder);
        User savedUser = userRepository.save(user);

        // Create requester profile
        RequesterProfile profile = RequesterRegistrationMapper.toProfile(request, savedUser);

        return requesterProfileRepository.save(profile);
    }
}
