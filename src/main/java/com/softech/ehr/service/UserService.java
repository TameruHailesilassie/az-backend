package com.softech.ehr.service;


import com.softech.ehr.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    Optional<User> getUserByEmail(String email);
}
