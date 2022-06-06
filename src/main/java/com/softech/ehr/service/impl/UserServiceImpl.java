package com.softech.ehr.service.impl;

import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.AzModelMapper;
import com.softech.ehr.dto.response.BasicUserDTO;
import com.softech.ehr.exception.NoUserFoundException;
import com.softech.ehr.repository.UserRepository;
import com.softech.ehr.service.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AzModelMapper modelMapper;

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
    public BasicUserDTO getUserByPhoneNumber(String phoneNumber) {

        return modelMapper.convertToUserDto(
            userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NoUserFoundException(
                    "User by Phone Number=>" + phoneNumber)));
    }

}
