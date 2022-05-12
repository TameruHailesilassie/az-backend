package com.softech.ehr.service.impl;

import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.EhrModelMapper;
import com.softech.ehr.dto.response.BasicUserDTO;
import com.softech.ehr.repository.UserRepository;
import com.softech.ehr.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public List<BasicUserDTO> getAllUsers() {
        return userRepository.findAll()
            .stream()
            .map(modelMapper::convertToUserDto)
            .collect(
                Collectors.toList());
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
