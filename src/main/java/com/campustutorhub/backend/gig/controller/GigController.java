package com.campustutorhub.backend.gig.controller;

import com.campustutorhub.backend.auth.model.User;
import com.campustutorhub.backend.auth.repository.UserRepository;
import com.campustutorhub.backend.auth.security.CurrentUser;
import com.campustutorhub.backend.auth.security.UserPrincipal;
import com.campustutorhub.backend.common.ApiResponse;
import com.campustutorhub.backend.gig.model.Gig;
import com.campustutorhub.backend.gig.service.GigService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gigs")
public class GigController {

    @Autowired
    private GigService gigService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @PreAuthorize("hasRole('TUTOR')")
    public ResponseEntity<ApiResponse<Gig>> createGig(@CurrentUser UserPrincipal currentUser,
                                                     @Valid @RequestBody Gig gig) {
        User tutor = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Tutor not found"));
        Gig createdGig = gigService.createGig(tutor, gig);
        return ResponseEntity.ok(ApiResponse.success(createdGig, "Gig created successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<Gig>>> searchGigs(
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String university,
            @RequestParam(required = false) Double minRank,
            Pageable pageable) {
        Page<Gig> gigs = gigService.searchGigs(subject, university, minRank, pageable);
        return ResponseEntity.ok(ApiResponse.success(gigs, "Gigs retrieved successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Gig>> getGig(@PathVariable Long id) {
        Gig gig = gigService.getGigById(id);
        return ResponseEntity.ok(ApiResponse.success(gig, "Gig retrieved successfully"));
    }
}

