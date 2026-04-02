package com.campustutorhub.backend.gig.repository;

import com.campustutorhub.backend.gig.model.Gig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GigRepository extends JpaRepository<Gig, Long> {
    Page<Gig> findBySubjectContainingIgnoreCase(String subject, Pageable pageable);
    
    @Query("SELECT g FROM Gig g JOIN g.tutor u JOIN Profile p ON p.user.id = u.id " +
           "WHERE (:subject IS NULL OR LOWER(g.subject) LIKE LOWER(CONCAT('%', :subject, '%'))) " +
           "AND (:university IS NULL OR LOWER(p.university) LIKE LOWER(CONCAT('%', :university, '%'))) " +
           "AND (:minRank IS NULL OR p.rank >= :minRank)")
    Page<Gig> searchGigs(@Param("subject") String subject, 
                         @Param("university") String university, 
                         @Param("minRank") Double minRank, 
                         Pageable pageable);
}

