package com.blooddon.backend.controllers;

import com.blooddon.backend.dto.LoginRequest;
import com.blooddon.backend.dto.LoginResponse;
import com.blooddon.backend.dto.RegisterDonorRequest;
import com.blooddon.backend.dto.RegisterRequesterRequest;
import com.blooddon.backend.models.DonorProfile;
import com.blooddon.backend.models.RequesterProfile;
import com.blooddon.backend.services.AuthService;
import com.blooddon.backend.services.RegisterationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RegisterationService registrationService;
    private final AuthService authService ;


    public AuthController(RegisterationService registrationService,AuthService authService) {
        this.registrationService = registrationService;
        this.authService = authService;
    }


    @PostMapping("/register-donor")
    public ResponseEntity<DonorProfile> registerDonor(@Valid @RequestBody RegisterDonorRequest request) {
        DonorProfile profile = registrationService.registerDonor(request);
        return ResponseEntity.ok(profile);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register-requester")
    public ResponseEntity<RequesterProfile> registerRequester(
            @Valid @RequestBody RegisterRequesterRequest request
    ) {
        RequesterProfile profile = registrationService.registerRequester(request);
        return ResponseEntity.ok(profile);
    }

}

