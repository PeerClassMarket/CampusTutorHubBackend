package com.campustutorhub.backend.gig.service;

import com.campustutorhub.backend.auth.model.User;
import com.campustutorhub.backend.gig.model.Booking;
import com.campustutorhub.backend.gig.model.Review;
import com.campustutorhub.backend.gig.repository.BookingRepository;
import com.campustutorhub.backend.gig.repository.ReviewRepository;
import com.campustutorhub.backend.profile.model.Profile;
import com.campustutorhub.backend.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ProfileService profileService;

    @Transactional
    public Review createReview(User student, Long bookingId, Review review) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
                
        if (!booking.getStudent().getId().equals(student.getId())) {
            throw new RuntimeException("Only the student who booked can review");
        }

        review.setBooking(booking);
        review.setStudent(student);
        review.setTutor(booking.getGig().getTutor());
        
        Review savedReview = reviewRepository.save(review);
        
        // Update tutor rank
        updateTutorRank(booking.getGig().getTutor().getId());
        
        return savedReview;
    }

    private void updateTutorRank(Long tutorId) {
        List<Review> reviews = reviewRepository.findByTutorId(tutorId);
        double average = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
                
        Profile profile = profileService.getProfileByUserId(tutorId);
        profile.setRank(average);
        // Profile is saved by profileService or manually if needed
    }
}

