package com.campustutorhub.backend.profile.payload;

import lombok.Data;

@Data
public class ProfileUpdateRequest {
    private String bio;
    private String university;
    private String specialization;
    private String profilePictureUrl;
    private String phoneNumber;
}
