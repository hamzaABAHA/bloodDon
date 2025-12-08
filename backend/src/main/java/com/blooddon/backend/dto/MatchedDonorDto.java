package com.blooddon.backend.dto;

// FIX: Add getters and setters so the object can be serialized to JSON.
public class MatchedDonorDto {
    private Long id;
    private String fullName;
    private String phone;
    private String bloodType;
    private String city;

    // --- GETTERS ---
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public String getBloodType() { return bloodType; }
    public String getCity() { return city; }

    // --- SETTERS ---
    public void setId(Long id) { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }
    public void setCity(String city) { this.city = city; }
}
