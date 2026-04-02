package com.campustutorhub.backend.gig.controller;

import com.campustutorhub.backend.auth.model.User;
import com.campustutorhub.backend.auth.repository.UserRepository;
import com.campustutorhub.backend.auth.security.CurrentUser;
import com.campustutorhub.backend.auth.security.UserPrincipal;
import com.campustutorhub.backend.common.ApiResponse;
import com.campustutorhub.backend.gig.model.Review;
import com.campustutorhub.backend.gig.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<Review>> createReview(@CurrentUser UserPrincipal currentUser,
                                                           @PathVariable Long bookingId,
                                                           @Valid @RequestBody Review review) {
        User student = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Review createdReview = reviewService.createReview(student, bookingId, review);
        return ResponseEntity.ok(ApiResponse.success(createdReview, "Review submitted successfully"));
    }
}

