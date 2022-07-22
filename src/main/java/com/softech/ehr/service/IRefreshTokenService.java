package com.softech.ehr.service;

import com.softech.ehr.domain.entity.RefreshToken;
import com.softech.ehr.domain.entity.User;

import java.util.Optional;

public interface IRefreshTokenService {

    Optional<RefreshToken> getToken(String refreshToken);

    RefreshToken generateToken(User user);

    Optional<RefreshToken> verifyExpiration(RefreshToken token);
}
