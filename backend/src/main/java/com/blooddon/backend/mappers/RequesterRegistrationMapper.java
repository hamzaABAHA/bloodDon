package com.blooddon.backend.mappers;

import com.blooddon.backend.dto.RegisterRequesterRequest;
import com.blooddon.backend.models.RequesterProfile;
import com.blooddon.backend.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RequesterRegistrationMapper {

    public static User toUser(RegisterRequesterRequest dto, PasswordEncoder encoder) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword())); // 🔥 HASHED
        user.setRole(User.Role.REQUESTER);
        return user;
    }


    public static RequesterProfile toProfile(RegisterRequesterRequest dto, User savedUser) {
        RequesterProfile profile = new RequesterProfile();
        profile.setUser(savedUser);
        profile.setFullName(dto.getFullName());
        profile.setOrganizationName(dto.getOrganizationName());
        profile.setCity(dto.getCity());
        profile.setPhoneNumber(dto.getPhone());
        return profile;
    }
}
