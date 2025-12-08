package com.blooddon.backend.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- RELATIONSHIPS ---
    @ManyToOne(fetch = FetchType.LAZY) // Use LAZY fetch for performance
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "blood_request_id") // Recommended for OneToMany
    private List<Review> reviews = new ArrayList<>();

    // --- DENORMALIZED REQUESTER INFO ---
    // Stored for quick display without joining User/RequesterProfile
    private String requesterFullName;
    private String requesterPhone;

    // --- REQUEST DETAILS ---
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BloodType bloodType;

    private String caseDescription;
    private String city;
    private double latitude;
    private double longitude;
    private String locationLabel;

    @Column(length = 500)
    private String motivationMessage;

    // --- MATCHING & STATE ---
    // Stores the USER IDs of donors who have been matched.
    @ElementCollection
    @CollectionTable(name = "blood_request_matched_donors", joinColumns = @JoinColumn(name = "blood_request_id"))
    @Column(name = "user_id")
    private List<Long> matchedDonorIds = new ArrayList<>();

    private boolean isCompleted = false;

    // --- TIMESTAMPS ---
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public BloodRequest() {}

    // --- FIX: REMOVED STATIC fromDTO METHOD ---
    // This logic belongs in a dedicated Mapper class, not the entity.

    // --- LIFECYCLE CALLBACKS ---
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // --- GETTERS & SETTERS ---

    public Long getId() { return id; }

    public User getRequester() { return requester; }
    public void setRequester(User requester) { this.requester = requester; }

    public String getRequesterFullName() { return requesterFullName; }
    public void setRequesterFullName(String requesterFullName) { this.requesterFullName = requesterFullName; }

    public String getRequesterPhone() { return requesterPhone; }
    public void setRequesterPhone(String requesterPhone) { this.requesterPhone = requesterPhone; }

    public BloodType getBloodType() { return bloodType; }
    public void setBloodType(BloodType bloodType) { this.bloodType = bloodType; }

    public String getCaseDescription() { return caseDescription; }
    public void setCaseDescription(String caseDescription) { this.caseDescription = caseDescription; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getLocationLabel() { return locationLabel; }
    public void setLocationLabel(String locationLabel) { this.locationLabel = locationLabel; }

    public String getMotivationMessage() { return motivationMessage; }
    public void setMotivationMessage(String motivationMessage) { this.motivationMessage = motivationMessage; }

    public List<Long> getMatchedDonorIds() { return matchedDonorIds; }
    public void setMatchedDonorIds(List<Long> matchedDonorIds) { this.matchedDonorIds = matchedDonorIds; }



    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
