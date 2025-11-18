package com.blooddon.backend.services;

import com.blooddon.backend.dto.LoginRequest;
import com.blooddon.backend.dto.LoginResponse;
import com.blooddon.backend.models.User;
import com.blooddon.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        // ⚠ For now: plain-text compare. Later we will hash.
        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return new LoginResponse(user.getId(), user.getEmail(), user.getRole().name());
    }


}
