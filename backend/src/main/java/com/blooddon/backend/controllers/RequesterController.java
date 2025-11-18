package com.blooddon.backend.controllers;

import com.blooddon.backend.models.RequesterProfile;
import com.blooddon.backend.repositories.RequesterProfileRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requesters")
public class RequesterController {

    private final RequesterProfileRepository requesterProfileRepository;

    public RequesterController(RequesterProfileRepository requesterProfileRepository) {
        this.requesterProfileRepository = requesterProfileRepository;
    }

    @GetMapping
    public List<RequesterProfile> getAllRequesters() {
        return requesterProfileRepository.findAll();
    }
}
