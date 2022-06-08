package com.softech.ehr.controller;

import com.softech.ehr.dto.response.UserDto;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface IUserController {

    @GetMapping("/list")
    Page<UserDto> getAllUsers(@RequestParam int page,
                              @RequestParam int size);

    @GetMapping()
    ResponseEntity<UserDto> getUserByPhone(
        @PathVariable("phone") String phoneNUmber);


    @GetMapping("/test")
    String testUserController();
}
