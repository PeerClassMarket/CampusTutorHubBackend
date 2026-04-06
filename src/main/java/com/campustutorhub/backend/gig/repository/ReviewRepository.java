package com.campustutorhub.backend.gig.repository;

import com.campustutorhub.backend.gig.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTutorId(Long tutorId);

    @Query("SELECT r FROM Review r WHERE r.booking.gig.id = :gigId")
    List<Review> findByGigId(@Param("gigId") Long gigId);
}

