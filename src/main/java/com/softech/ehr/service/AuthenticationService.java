package com.softech.ehr.service;

import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.request.AuthenticationRequest;
import com.softech.ehr.dto.request.UserRegistrationDTO;
import com.softech.ehr.dto.response.AuthenticationResponse;
import com.softech.ehr.dto.response.BasicUserDTO;

public interface AuthenticationService {
    AuthenticationResponse authenticate(
        AuthenticationRequest authenticationRequest);

    AuthenticationResponse refreshToken(String token);

    BasicUserDTO registerUser(UserRegistrationDTO userRegistrationRequest);
}
