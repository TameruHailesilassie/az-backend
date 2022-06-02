package com.softech.ehr.service;

import com.softech.ehr.dto.response.BasicUserDTO;

import org.springframework.data.domain.Page;

public interface UserService {

    Page<BasicUserDTO> getAllUsers(int page, int size);

    BasicUserDTO getUserByPhoneNumber(String phoneNumber);

}
