package com.blooddon.backend.dto;

public class RequesterProfileDto {

    private Long id;
    private String fullName;
    private String organizationName;
    private String city;
    private String phoneNumber;
    private String email;

    public RequesterProfileDto() {}

    public RequesterProfileDto(Long id, String fullName, String organizationName,
                               String city, String phoneNumber, String email) {
        this.id = id;
        this.fullName = fullName;
        this.organizationName = organizationName;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // getters only (frontend display)
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getOrganizationName() { return organizationName; }
    public String getCity() { return city; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
}
