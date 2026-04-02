package com.campustutorhub.backend.gig.controller;

import com.campustutorhub.backend.auth.model.User;
import com.campustutorhub.backend.auth.repository.UserRepository;
import com.campustutorhub.backend.auth.security.CurrentUser;
import com.campustutorhub.backend.auth.security.UserPrincipal;
import com.campustutorhub.backend.common.ApiResponse;
import com.campustutorhub.backend.gig.model.Booking;
import com.campustutorhub.backend.gig.model.BookingStatus;
import com.campustutorhub.backend.gig.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{gigId}")
    public ResponseEntity<ApiResponse<Booking>> createBooking(@CurrentUser UserPrincipal currentUser,
                                                             @PathVariable Long gigId) {
        User student = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Booking booking = bookingService.createBooking(student, gigId);
        return ResponseEntity.ok(ApiResponse.success(booking, "Booking requested successfully"));
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<ApiResponse<Page<Booking>>> getMyBookings(@CurrentUser UserPrincipal currentUser,
                                                                  Pageable pageable) {
        Page<Booking> bookings = bookingService.getStudentBookings(currentUser.getId(), pageable);
        return ResponseEntity.ok(ApiResponse.success(bookings, "Bookings retrieved successfully"));
    }

    @GetMapping("/tutor-requests")
    public ResponseEntity<ApiResponse<Page<Booking>>> getTutorRequests(@CurrentUser UserPrincipal currentUser,
                                                                     Pageable pageable) {
        Page<Booking> bookings = bookingService.getTutorBookings(currentUser.getId(), pageable);
        return ResponseEntity.ok(ApiResponse.success(bookings, "Tutor requests retrieved successfully"));
    }

    @PatchMapping("/{bookingId}/status")
    public ResponseEntity<ApiResponse<Booking>> updateStatus(@PathVariable Long bookingId,
                                                            @RequestParam BookingStatus status) {
        Booking booking = bookingService.updateBookingStatus(bookingId, status);
        return ResponseEntity.ok(ApiResponse.success(booking, "Booking status updated successfully"));
    }
}

