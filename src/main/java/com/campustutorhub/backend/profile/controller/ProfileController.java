package com.campustutorhub.backend.profile.controller;

import com.campustutorhub.backend.auth.security.CurrentUser;
import com.campustutorhub.backend.auth.security.UserPrincipal;
import com.campustutorhub.backend.common.ApiResponse;
import com.campustutorhub.backend.profile.model.Profile;
import com.campustutorhub.backend.profile.payload.ProfileUpdateRequest;
import com.campustutorhub.backend.profile.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Profile>> getCurrentUserProfile(@CurrentUser UserPrincipal currentUser) {
        Profile profile = profileService.getProfileByUserId(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success(profile, "Profile retrieved successfully"));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<Profile>> updateProfile(@CurrentUser UserPrincipal currentUser,
                                                             @Valid @RequestBody ProfileUpdateRequest request) {
        Profile profile = profileService.updateProfile(currentUser.getId(), request);
        return ResponseEntity.ok(ApiResponse.success(profile, "Profile updated successfully"));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Profile>> getUserProfile(@PathVariable Long userId) {
        Profile profile = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(profile, "Profile retrieved successfully"));
    }
}
