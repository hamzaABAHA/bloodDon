package com.blooddon.backend.mappers;

import com.blooddon.backend.dto.RegisterDonorRequest;
import com.blooddon.backend.models.BloodType;
import com.blooddon.backend.models.DonorProfile;
import com.blooddon.backend.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DonorMapper {

    public static User toUser(RegisterDonorRequest dto, PasswordEncoder encoder) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword())); // 🔥 HASHED
        user.setRole(User.Role.DONOR);
        return user;
    }



    public static BloodType convertBloodType(String input) {
        switch (input.trim().toUpperCase()) {
            case "A+":
                return BloodType.A_POS;
            case "A-":
                return BloodType.A_NEG;

            case "B+":
                return BloodType.B_POS;
            case "B-":
                return BloodType.B_NEG;

            case "O+":
                return BloodType.O_POS;
            case "O-":
                return BloodType.O_NEG;

            case "AB+":
                return BloodType.AB_POS;
            case "AB-":
                return BloodType.AB_NEG;

            default:
                throw new IllegalArgumentException("Invalid blood type: " + input);
        }
    }


    public static DonorProfile toDonorProfile(RegisterDonorRequest dto, User savedUser) {
        DonorProfile profile = new DonorProfile();
        profile.setUser(savedUser);
        profile.setFullName(dto.getFullName());
        profile.setCity(dto.getCity());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setBloodType(dto.getBloodType());
        profile.setAvailable(true);
        return profile;
    }
}
