package com.softech.ehr.service.impl;

import com.softech.ehr.domain.entity.Role;
import com.softech.ehr.domain.entity.Salary;
import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.AzModelMapper;
import com.softech.ehr.dto.request.AuthenticationRequest;
import com.softech.ehr.dto.request.UserRegistrationDTO;
import com.softech.ehr.dto.response.AuthenticationResponse;
import com.softech.ehr.dto.response.BasicUserDTO;
import com.softech.ehr.exception.EntityNotFoundException;
import com.softech.ehr.model.security.SecurityUser;
import com.softech.ehr.repository.RoleRepository;
import com.softech.ehr.repository.SpecializationRepository;
import com.softech.ehr.repository.UserRepository;
import com.softech.ehr.security.TokenUtils;
import com.softech.ehr.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.transaction.Transactional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final SpecializationRepository specializationRepository;
    private final AzModelMapper modelMapper;


    @Autowired
    public AuthenticationServiceImpl(
        AuthenticationManager authenticationManager,
        TokenUtils tokenUtils,
        UserDetailsService userDetailsService,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        RoleRepository roleRepository,
        SpecializationRepository specializationRepository,
        AzModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.specializationRepository = specializationRepository;
        this.modelMapper = modelMapper;
    }

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
    @Transactional
    public BasicUserDTO registerUser(
        UserRegistrationDTO userRegistrationRequest) {

        String hashedPassword = passwordEncoder.encode("azEhr");
        Set<Role> userRoles = new HashSet<>();
        userRegistrationRequest.getRoles().forEach(role -> {
            userRoles.add(roleRepository.findByName(role)
                .orElseThrow(
                    () -> new EntityNotFoundException("Role:" + role)));
        });

        //User
        User newUser = User.builder()
            .firstName(userRegistrationRequest.getFirstName())
            .middleName(userRegistrationRequest.getMiddleName())
            .lastName(userRegistrationRequest.getLastName())
            .sex(userRegistrationRequest.getSex())
            .roles(userRoles)
            .phoneNumber(userRegistrationRequest.getPhoneNumber())
            .doctorsFee(new ArrayList<>())
            .employment(userRegistrationRequest.getEmployment())
            .enabled(true)
            .dateStarted(userRegistrationRequest.getDateStarted())
            .contractDate(userRegistrationRequest.getContractDate())
            .createdDate(LocalDateTime.now())
            .title(userRegistrationRequest.getTitle())
            .password(hashedPassword)
            .build();

        //Specialization
        if (userRegistrationRequest.getSpecialization() != null) {
            newUser.specialization(
                specializationRepository.findById(
                        userRegistrationRequest.getSpecialization())
                    .orElseThrow(() -> new EntityNotFoundException(
                        "Specialization  not found by ID:" +
                            userRegistrationRequest.getSpecialization()))
            );
        }

        //Doctors Charge
        if (userRegistrationRequest.getDoctorsCharge() != null) {
            userRegistrationRequest
                .getDoctorsCharge().forEach(newUser::addDoctorsCharge);
        }

        //Salary
        Salary salary = Salary.builder()
            .amount(userRegistrationRequest.getSalaryAmount())
            .type(userRegistrationRequest.getSalaryType())
            .build();
        newUser.salary(salary);
        //Address
        newUser.address(userRegistrationRequest.getAddress());

        return modelMapper
            .convertToUserDto
                (userRepository.save(newUser));
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
