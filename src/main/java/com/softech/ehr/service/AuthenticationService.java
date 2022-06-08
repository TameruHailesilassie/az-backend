package com.softech.ehr.service;

import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.request.AuthPostDto;
import com.softech.ehr.dto.response.AuthDto;

public interface AuthenticationService {
    AuthDto authenticate(
        AuthPostDto authenticationRequest);

    AuthDto refreshToken(String token);

    User registerUser(User user);
}

