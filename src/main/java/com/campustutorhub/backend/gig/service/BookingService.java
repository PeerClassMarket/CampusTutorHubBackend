package com.campustutorhub.backend.gig.service;

import com.campustutorhub.backend.auth.model.User;
import com.campustutorhub.backend.gig.model.Booking;
import com.campustutorhub.backend.gig.model.BookingStatus;
import com.campustutorhub.backend.gig.model.Gig;
import com.campustutorhub.backend.gig.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GigService gigService;

    @Transactional
    public Booking createBooking(User student, Long gigId, LocalDateTime startTime, LocalDateTime endTime) {
        Gig gig = gigService.getGigById(gigId);
        
        Booking booking = Booking.builder()
                .gig(gig)
                .student(student)
                .status(BookingStatus.PENDING)
                .startTime(startTime)
                .endTime(endTime)
                .meetingChannel("channel-" + student.getId() + "-" + gigId + "-" + System.currentTimeMillis())
                .build();
                
        return bookingRepository.save(booking);
    }

    public Page<Booking> getStudentBookings(Long studentId, Pageable pageable) {
        return bookingRepository.findByStudentId(studentId, pageable);
    }

    public Page<Booking> getTutorBookings(Long tutorId, Pageable pageable) {
        return bookingRepository.findByTutorId(tutorId, pageable);
    }

    @Transactional
    public Booking updateBookingStatus(Long bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }
}

