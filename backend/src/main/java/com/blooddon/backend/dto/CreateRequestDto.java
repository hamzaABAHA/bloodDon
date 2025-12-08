package com.blooddon.backend.dto;

import com.blooddon.backend.models.BloodType;


public class CreateRequestDto {

    private String requesterFullName;
    private String requesterPhone;
    private BloodType bloodType;
    private String caseDescription;
    private String city;
    private Long requesterId;

    // Map location
    private double latitude;
    private double longitude;
    private String locationLabel;
    private String motivationMessage;

    public CreateRequestDto() {}

    public Long getRequesterId() { return requesterId; }
    public void setRequesterId(Long requesterId) { this.requesterId = requesterId; }

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
    public void setMotivationMessage(String motivationMessage) { this.motivationMessage = motivationMessage;}
}

