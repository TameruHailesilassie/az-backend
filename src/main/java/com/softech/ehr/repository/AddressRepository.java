package com.softech.ehr.repository;

import java.util.Optional;

import com.softech.ehr.domain.entity.Address;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByEmail(String email);
}
