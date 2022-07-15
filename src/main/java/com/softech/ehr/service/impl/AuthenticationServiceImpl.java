package com.softech.ehr.service.impl;

import com.softech.ehr.domain.entity.RefreshToken;
import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.AzModelMapper;
import com.softech.ehr.dto.request.AuthPostDto;
import com.softech.ehr.dto.response.AuthDto;
import com.softech.ehr.dto.response.RoleDto;
import com.softech.ehr.dto.response.UserAuthDto;
import com.softech.ehr.exception.TokenRefreshException;
import com.softech.ehr.exception.UserAlreadyExistException;
import com.softech.ehr.repository.RefreshTokenRepository;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@EnableAsync
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final String REFRESH_COOKIE = "refresh_token";
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AzModelMapper modelMapper;
    private final RefreshTokenServiceImpl refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public AuthDto authenticate(
        AuthPostDto authenticationRequest, HttpServletResponse response) {
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
        return generateAuthResponse(userDetails, authenticationRequest,
            response);
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

     //TODO response time performance issue... taking around.. 7sec
    @Override
    public AuthDto refreshToken(HttpServletRequest request,
                                HttpServletResponse response) {

        String rtToken = this.readCookie(request)
            .orElseThrow(() ->
                new TokenRefreshException(
                    "Invalid Cookie not found. Please make a new sign in request!"));


        RefreshToken refreshToken = this.refreshTokenService.getToken(rtToken)
            .orElseThrow(() ->
                new TokenRefreshException(
                    "Refresh Token not found. Please make a new sign in request!"));

        RefreshToken validRefreshToken =
            this.refreshTokenService.verifyExpiration(refreshToken)
                .orElseThrow(() ->
                    new TokenRefreshException(
                        "Refresh Token was expired. Please make a new sign in request!"));

        User user = validRefreshToken.getUser();

        //invalidate token
        this.refreshTokenRepository.delete(validRefreshToken);
        return generateAuthResponse(
            this.userDetailsService.loadUserByUsername(user.getPhoneNumber()),
            null, response);

    }

    private User fetchUserByUserName(String userName) {
        return this.userRepository.findByPhoneNumber(userName)
            .orElseThrow(
                () -> new UsernameNotFoundException(
                    "No user found by phone number " +
                        userName));
    }

    @Transactional
    AuthDto generateAuthResponse(
        UserDetails userDetails,
        AuthPostDto authenticationRequest,
        HttpServletResponse response) {

        //create a user Data
        User user = fetchUserByUserName(userDetails.getUsername());
        UserAuthDto userData = getUserData(user);

        //generate and save refresh token
        RefreshToken refreshToken =
            this.refreshTokenService.generateToken(user);
        this.refreshTokenRepository.save(refreshToken);

        //set cookie
        response.addCookie(this.crateCookie(refreshToken));

        //generate access token min expiry date
        String accessToken = this.tokenUtils.generateToken(userDetails,
            authenticationRequest != null ? authenticationRequest.getDevice() :
                "web");

        //generate Auth Response DTO
        return AuthDto.builder()
            .userData(userData)
            .accessToken(accessToken)
            .build();
    }

    private Optional<String> readCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
            .filter(cookie -> REFRESH_COOKIE.equals(cookie.getName()))
            .map(Cookie::getValue)
            .findAny();
    }

    private Cookie crateCookie(RefreshToken token) {
        Cookie cookie = new Cookie(REFRESH_COOKIE, token.getToken());
        cookie.setHttpOnly(true);
        //TODO for dev only
        cookie.setSecure(false);
        return cookie;
    }


    private UserAuthDto getUserData(User user) {
        //create a user Data
        List<RoleDto> roles = user.getRoles()
            .stream()
            .map(modelMapper::toRoleDto)
            .collect(Collectors.toList());

        return UserAuthDto.builder()
            .firstName(user.getFirstName())
            .middleName(user.getMiddleName())
            .lastName(user.getLastName())
            .phoneNumber(user.getPhoneNumber())
            .roles(roles)
            .build();
    }


}
