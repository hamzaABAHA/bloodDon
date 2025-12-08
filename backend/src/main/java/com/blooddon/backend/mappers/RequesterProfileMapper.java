package com.blooddon.backend.mappers;

import com.blooddon.backend.dto.RequesterProfileDto;
import com.blooddon.backend.models.RequesterProfile;

public class RequesterProfileMapper {

    public static RequesterProfileDto toDto(RequesterProfile profile) {
        return new RequesterProfileDto(
                profile.getId(),
                profile.getFullName(),
                profile.getOrganizationName(),
                profile.getCity(),
                profile.getPhoneNumber(),
                profile.getUser().getEmail()  // from User entity
        );
    }

    public static void fromDto(RequesterProfileDto dto, RequesterProfile profile) {
        profile.setFullName(dto.getFullName());
        profile.setOrganizationName(dto.getOrganizationName());
        profile.setCity(dto.getCity());
        profile.setPhoneNumber(dto.getPhoneNumber());
        //sm3ti: email is NOT set here, because it's part of User, not RequesterProfile
        // updating email should be done via UserService if needed
    }
}
