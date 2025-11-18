package com.blooddon.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "requester_profiles")
public class RequesterProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String fullName;  // name of the person who posts requests

    @Column(nullable = false)
    private String organizationName; // hospital, clinic, or "individual"

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String phone; // contact for emergencies

    public RequesterProfile() {
    }

    public RequesterProfile(User user, String fullName, String organizationName, String city, String phone) {
        this.user = user;
        this.fullName = fullName;
        this.organizationName = organizationName;
        this.city = city;
        this.phone = phone;
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

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
