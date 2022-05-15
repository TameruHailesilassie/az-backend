package com.softech.ehr.controller;

import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.response.BasicUserDTO;
import com.softech.ehr.dto.response.UsersResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface IUserController {

    @GetMapping()
    ResponseEntity<UsersResponse> getAllUsers();


    @GetMapping("/{username}")
    ResponseEntity<BasicUserDTO> getUserByEmail(
        @PathVariable("username") String username);


    @GetMapping("/test")
    String testUserController();
}
