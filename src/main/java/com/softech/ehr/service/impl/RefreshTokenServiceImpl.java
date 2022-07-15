package com.softech.ehr.service.impl;

import com.softech.ehr.domain.entity.RefreshToken;
import com.softech.ehr.domain.entity.User;
import com.softech.ehr.repository.RefreshTokenRepository;
import com.softech.ehr.service.RefreshTokenService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository rtRepository;
    @Value("${az.refresh.token.expiryDate}")
    private Long expiryDateMil;


    @Override
    public Optional<RefreshToken> getToken(String refreshToken) {

        return rtRepository.findByToken(refreshToken);
    }

    @Override
    public RefreshToken generateToken(User user) {
        return RefreshToken.builder()
            .expires_at(Instant.now().plusMillis(expiryDateMil))
            .last_used_at(Instant.now())
            .user(user)
            .token(UUID.randomUUID().toString())
            .build();
    }


    @Override
    public Optional<RefreshToken> verifyExpiration(RefreshToken token) {
        //if expiration date is less than current date
        if (token.getExpires_at().compareTo(Instant.now()) < 0) {
            rtRepository.delete(token);
            return Optional.empty();
        }
        return Optional.of(token);
    }
}
