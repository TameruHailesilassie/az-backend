package com.softech.ehr.service.impl;

import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.request.AuthenticationRequest;
import com.softech.ehr.dto.response.AuthenticationResponse;
import com.softech.ehr.model.security.SecurityUser;
import com.softech.ehr.repository.UserRepository;
import com.softech.ehr.security.TokenUtils;
import com.softech.ehr.service.AuthenticationService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Override
    public AuthenticationResponse authenticate(
        AuthenticationRequest authenticationRequest) {
        // Perform the authentication
        Authentication authentication = this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-authentication so we can generate token
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(
            authenticationRequest.getUsername());
        User user = fetchUserByUserName(userDetails.getUsername());

        return generateResponse(user, userDetails, authenticationRequest);
    }

    @Override
    public AuthenticationResponse refreshToken(String token) {
        String username = this.tokenUtils.getUsernameFromToken(token);
        SecurityUser securityUser =
            (SecurityUser) this.userDetailsService.loadUserByUsername(username);
        if (this.tokenUtils.canTokenBeRefreshed(token,
            securityUser.getLastPasswordReset())) {
            User user = fetchUserByUserName(username);
            return new AuthenticationResponse(
                user.roles(),
                username,
                user.firstName(),
                user.middleName(),
                this.tokenUtils.generateToken(securityUser, "")
            );
        }
        return new AuthenticationResponse();
    }

    @Override
    public User registerUser(AuthenticationRequest authenticationRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword =
            passwordEncoder.encode(authenticationRequest.getPassword());
        //TODO add roles
        User newUser = User.builder()
            //   .password(hashedPassword)
            // .email(authenticationRequest.getEmail())
            .build();
        return userRepository.save(newUser);
    }

    private User fetchUserByUserName(String userName) {
        return this.userRepository.findByPhoneNumber(userName)
            .orElseThrow(
                () -> new UsernameNotFoundException(
                    "No user found by phone number " +
                        userName));
    }

    private AuthenticationResponse generateResponse(User user,
                                                    UserDetails userDetails,
                                                    AuthenticationRequest authenticationRequest) {
        return new AuthenticationResponse(
            user.roles(),
            user.phoneNumber(),
            user.firstName(),
            user.middleName(),
            this.tokenUtils.generateToken(userDetails,
                authenticationRequest.getDevice()));
    }
}
