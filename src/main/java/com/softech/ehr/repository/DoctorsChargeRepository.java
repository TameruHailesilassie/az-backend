package com.softech.ehr.repository;

import java.util.Optional;

import com.softech.ehr.domain.entity.DoctorsCharge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorsChargeRepository extends JpaRepository<DoctorsCharge, Long> {

    Optional<DoctorsCharge> findById(Long aLong);
    Optional<DoctorsCharge> findByName(String name);
}
