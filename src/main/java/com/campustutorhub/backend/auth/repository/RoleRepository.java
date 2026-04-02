package com.campustutorhub.backend.auth.repository;

import com.campustutorhub.backend.auth.model.Role;
import com.campustutorhub.backend.auth.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
