package com.campustutorhub.backend.gig.service;

import com.campustutorhub.backend.auth.model.User;
import com.campustutorhub.backend.gig.model.Gig;
import com.campustutorhub.backend.gig.repository.GigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GigService {

    @Autowired
    private GigRepository gigRepository;

    public Gig createGig(User tutor, Gig gig) {
        gig.setTutor(tutor);
        return gigRepository.save(gig);
    }

    @org.springframework.cache.annotation.Cacheable(value = "gigs", key = "{#subject, #university, #minRank, #pageable}")
    public Page<Gig> searchGigs(String subject, String university, Double minRank, Pageable pageable) {
        return gigRepository.searchGigs(subject, university, minRank, pageable);
    }

    public Gig getGigById(Long id) {
        return gigRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gig not found with id: " + id));
    }
}
