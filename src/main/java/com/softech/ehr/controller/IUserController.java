package com.softech.ehr.controller;

import com.softech.ehr.dto.response.BasicUserDTO;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface IUserController {

    @GetMapping()
    Page<BasicUserDTO> getAllUsers(@RequestParam int page,
                                   @RequestParam int size);

    @GetMapping("/{username}")
    ResponseEntity<BasicUserDTO> getUserByEmail(
        @PathVariable("username") String username);


    @GetMapping("/test")
    String testUserController();
}
