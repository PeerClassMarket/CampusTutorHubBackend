package com.campustutorhub.backend.gig.repository;

import com.campustutorhub.backend.gig.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTutorId(Long tutorId);
    List<Review> findByGigId(Long gigId);
}

