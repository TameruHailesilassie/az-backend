package com.softech.ehr.repository;

import com.softech.ehr.domain.entity.Specialization;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    Optional<Specialization> findById(Long id);

}
