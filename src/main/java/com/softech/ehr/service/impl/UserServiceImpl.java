package com.softech.ehr.service.impl;

import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.AzUserMapper;
import com.softech.ehr.dto.response.UserDto;
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
    private final AzUserMapper userMapper;

    @Override
    public Page<UserDto> getAllUsers(int page, int size) {
        Pageable pr = PageRequest.of(page, size);
        Page<User> result = userRepository.findAll(pr);
        return new PageImpl<>(
            result
                .stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList())
            , pr, result.getTotalElements());
    }

    @Override
    public UserDto getUserByPhoneNumber(String phoneNumber) {

        return userMapper.toUserDto(
            userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NoUserFoundException(
                    phoneNumber)));
    }

}
