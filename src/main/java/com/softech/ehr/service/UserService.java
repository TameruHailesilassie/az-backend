package com.softech.ehr.service;

import com.softech.ehr.dto.response.UserDto;

import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserDto> getAllUsers(int page, int size);

    UserDto getUserByPhoneNumber(String phoneNumber);

}
