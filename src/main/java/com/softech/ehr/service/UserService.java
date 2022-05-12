package com.softech.ehr.service;


import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.response.BasicUserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<BasicUserDTO> getAllUsers();

    Optional<User> getUserByEmail(String email);
}
