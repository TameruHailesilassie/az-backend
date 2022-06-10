package com.softech.ehr.service.impl;

import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.request.AuthPostDto;
import com.softech.ehr.dto.response.AuthDto;
import com.softech.ehr.exception.UserAlreadyExistException;
import com.softech.ehr.model.security.SecurityUser;
import com.softech.ehr.repository.UserRepository;
import com.softech.ehr.security.TokenUtils;
import com.softech.ehr.service.AuthenticationService;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@EnableAsync
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthDto authenticate(
        AuthPostDto authenticationRequest) {
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
    public AuthDto refreshToken(String token) {
        String username = this.tokenUtils.getUsernameFromToken(token);
        SecurityUser securityUser =
            (SecurityUser) this.userDetailsService.loadUserByUsername(username);
        if (this.tokenUtils.canTokenBeRefreshed(token,
            securityUser.getLastPasswordReset())) {
            User user = fetchUserByUserName(username);
            return new AuthDto(
                user.getRoles(),
                username,
                user.getFirstName(),
                user.getMiddleName(),
                this.tokenUtils.generateToken(securityUser, "")
            );
        }
        return new AuthDto();
    }

    @Override
    @Transactional
    public User registerUser(User user) {
        if (userRepository.existsByPhoneNumber(
            user.getPhoneNumber())) {
            throw new UserAlreadyExistException(
                user.getPhoneNumber());
        }
        //default password
        user.setPassword(passwordEncoder.encode("azEhr"));
        user.addAddress(user.getAddress());
        user.addSalary(user.getSalary());
        return userRepository.save(user);
    }

    private User fetchUserByUserName(String userName) {
        return this.userRepository.findByPhoneNumber(userName)
            .orElseThrow(
                () -> new UsernameNotFoundException(
                    "No user found by phone number " +
                        userName));
    }

    private AuthDto generateResponse(User user,
                                     UserDetails userDetails,
                                     AuthPostDto authenticationRequest) {
        return new AuthDto(
            user.getRoles(),
            user.getPhoneNumber(),
            user.getFirstName(),
            user.getMiddleName(),
            this.tokenUtils.generateToken(userDetails,
                authenticationRequest.getDevice()));
    }
}
