package com.softech.ehr.service;

import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.request.AuthPostDto;
import com.softech.ehr.dto.response.AuthDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    AuthDto authenticate(
        AuthPostDto authenticationRequest, HttpServletResponse response);

    User registerUser(User user);

    AuthDto refreshToken(HttpServletRequest request,
                         HttpServletResponse response);
}

