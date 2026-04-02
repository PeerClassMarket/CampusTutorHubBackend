package com.campustutorhub.backend.gig.repository;

import com.campustutorhub.backend.gig.model.Booking;
import com.campustutorhub.backend.gig.model.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findByStudentId(Long studentId, Pageable pageable);
    
    @Query("SELECT b FROM Booking b JOIN b.gig g WHERE g.tutor.id = :tutorId")
    Page<Booking> findByTutorId(@Param("tutorId") Long tutorId, Pageable pageable);
    
    List<Booking> findByStatus(BookingStatus status);
}
