package com.softech.ehr.controller;

import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.response.BasicUserDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/users")
public interface IUserController {

    @GetMapping()
    ResponseEntity<List<BasicUserDTO>> getAllUsers();

    @GetMapping("/{username}")
    ResponseEntity<User> getUserByName(
        @PathVariable("username") String username);


    @GetMapping("/test")
    String testUserController();
}
