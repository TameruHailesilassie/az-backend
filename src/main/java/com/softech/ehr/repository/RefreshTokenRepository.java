package com.softech.ehr.repository;

import java.util.Optional;

import com.softech.ehr.domain.entity.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);


}
