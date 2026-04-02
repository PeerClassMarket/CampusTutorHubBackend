package com.campustutorhub.backend.gig.model;

import com.campustutorhub.backend.auth.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "gigs", indexes = {
        @Index(name = "idx_gig_subject", columnList = "subject"),
        @Index(name = "idx_gig_price", columnList = "price")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank
    private String subject;

    private BigDecimal price;

    private Integer durationMinutes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id", nullable = false)
    private User tutor;
}

