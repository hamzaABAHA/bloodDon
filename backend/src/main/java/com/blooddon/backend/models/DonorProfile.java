package com.blooddon.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "donor_profiles")
public class DonorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BloodType bloodType = BloodType.UNKNOWN;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private boolean isAvailable = true;

    public DonorProfile() {
    }

    public DonorProfile(User user, String fullName, BloodType bloodType, String city) {
        this.user = user;
        this.fullName = fullName;
        this.bloodType = (bloodType != null) ? bloodType : BloodType.UNKNOWN;
        this.city = city;
        this.isAvailable = true; // default
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
