package com.campustutorhub.backend.profile.service;

import com.campustutorhub.backend.auth.model.User;
import com.campustutorhub.backend.auth.repository.UserRepository;
import com.campustutorhub.backend.profile.model.Profile;
import com.campustutorhub.backend.profile.payload.ProfileUpdateRequest;
import com.campustutorhub.backend.profile.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    public Profile getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId)
                .orElseGet(() -> {
                    // Create default profile if not exists
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));
                    Profile profile = Profile.builder()
                            .user(user)
                            .build();
                    return profileRepository.save(profile);
                });
    }

    @Transactional
    public Profile updateProfile(Long userId, ProfileUpdateRequest request) {
        Profile profile = getProfileByUserId(userId);

        if (request.getBio() != null) profile.setBio(request.getBio());
        if (request.getUniversity() != null) profile.setUniversity(request.getUniversity());
        if (request.getSpecialization() != null) profile.setSpecialization(request.getSpecialization());
        if (request.getProfilePictureUrl() != null) profile.setProfilePictureUrl(request.getProfilePictureUrl());
        if (request.getPhoneNumber() != null) profile.setPhoneNumber(request.getPhoneNumber());

        return profileRepository.save(profile);
    }
}
