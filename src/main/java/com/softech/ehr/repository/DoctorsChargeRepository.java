package com.softech.ehr.repository;

import java.util.Optional;

import com.softech.ehr.domain.entity.DoctorsFee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorsChargeRepository extends JpaRepository<DoctorsFee, Long> {

    Optional<DoctorsFee> findById(Long aLong);
    Optional<DoctorsFee> findByName(String name);
}
