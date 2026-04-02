package com.campustutorhub.backend.profile.model;

import com.campustutorhub.backend.auth.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profiles", indexes = {
        @Index(name = "idx_profile_university", columnList = "university"),
        @Index(name = "idx_profile_specialization", columnList = "specialization"),
        @Index(name = "idx_profile_rank", columnList = "tutor_rank")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private String bio;

    private String university;

    private String specialization;

    @Column(name = "tutor_rank")
    @Builder.Default
    private Double rank = 0.0;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    private String phoneNumber;
}
