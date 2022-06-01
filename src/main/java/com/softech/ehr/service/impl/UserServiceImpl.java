package com.softech.ehr.service.impl;

import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.EhrModelMapper;
import com.softech.ehr.dto.response.BasicUserDTO;
import com.softech.ehr.exception.NoUserFoundException;
import com.softech.ehr.repository.UserRepository;
import com.softech.ehr.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<BasicUserDTO> getAllUsers(int page, int size) {
        Pageable pr = PageRequest.of(page, size);
        Page<User> result = userRepository.findAll(pr);

        return new PageImpl<>(
            result
                .stream()
                .map(modelMapper::convertToUserDto)
                .collect(Collectors.toList())
            , pr, result.getTotalElements());
    }

    @Override
    public BasicUserDTO getUserByEmail(String email) {
        return modelMapper
            .convertToUserDto(userRepository.findByEmail(email).orElseThrow(
                () -> new NoUserFoundException(
                    "User not fund wit email=" + email)));
    }
}
