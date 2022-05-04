package com.softech.ehr.security;

import com.softech.ehr.dto.LoginDTO;
import com.softech.ehr.dto.LoginResponseDTO;
import com.softech.ehr.user.User;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;


    public LoginResponseDTO authenticate(LoginDTO login) {
        Authentication authentication = authenticationManager
            .authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(),
                    login.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.createToken(authentication);
        User user = (User) authentication.getPrincipal();
        log.info("user authenticated Successfully => " + user.toString());
        return new LoginResponseDTO(
            user.firstName(),
            user.middleName(),
            user.lastName(),
            user.email(),
            token,
            user.roles()
        );

    }


}
