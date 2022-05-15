package com.softech.ehr.service;

import com.softech.ehr.dto.response.BasicUserDTO;
import com.softech.ehr.dto.response.UsersResponse;

public interface UserService {

    UsersResponse getAllUsers();

    BasicUserDTO getUserByEmail(String email);

}
