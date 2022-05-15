package com.softech.ehr.service.impl;

import com.softech.ehr.dto.EhrModelMapper;
import com.softech.ehr.dto.response.BasicUserDTO;
import com.softech.ehr.dto.response.UsersResponse;
import com.softech.ehr.exception.NoUserFoundException;
import com.softech.ehr.repository.UserRepository;
import com.softech.ehr.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EhrModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           EhrModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UsersResponse getAllUsers() {
        return UsersResponse.builder()
            .users(
                userRepository.findAll()
                    .stream()
                    .map(modelMapper::convertToUserDto)
                    .collect(
                        Collectors.toList()))
            .meta("")
            .build();
    }

    @Override
    public BasicUserDTO getUserByEmail(String email) {
        return modelMapper
            .convertToUserDto(userRepository.findByEmail(email).orElseThrow(
                () -> new NoUserFoundException(
                    "User not fund wit email=" + email)));
    }
}
