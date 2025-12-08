package com.blooddon.backend.dto;

import java.time.LocalDateTime;

public class DonationHistoryDto {

    private Long requestId;
    private String requesterFullName;
    private String city;
    private String bloodType;
    private String caseDescription;
    private boolean completed;
    private LocalDateTime createdAt;
    private String reviewMessage;
    private Integer pointsEarned;

    public DonationHistoryDto(Long requestId, String requesterFullName, String city,
                              String bloodType, String caseDescription, boolean completed,
                              LocalDateTime createdAt, String reviewMessage, Integer pointsEarned) {
        this.requestId = requestId;
        this.requesterFullName = requesterFullName;
        this.city = city;
        this.bloodType = bloodType;
        this.caseDescription = caseDescription;
        this.completed = completed;
        this.createdAt = createdAt;
        this.reviewMessage = reviewMessage;
        this.pointsEarned = pointsEarned;
    }

    // GETTERS ONLY (frontend display)
    public Long getRequestId() { return requestId; }
    public String getRequesterFullName() { return requesterFullName; }
    public String getCity() { return city; }
    public String getBloodType() { return bloodType; }
    public String getCaseDescription() { return caseDescription; }
    public boolean isCompleted() { return completed; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getReviewMessage() { return reviewMessage; }
    public Integer getPointsEarned() { return pointsEarned; }
}
