package com.softech.ehr.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.request.AuthPostDto;
import com.softech.ehr.dto.request.UserPostDto;


public interface IAuthenticationController {


    @PostMapping("/login")
    ResponseEntity<?> authenticationRequest(@RequestBody @Valid AuthPostDto authenticationRequest);
    @GetMapping("/refresh")
    ResponseEntity<?> authenticationRequest(HttpServletRequest request);

    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody @Valid UserPostDto userPostDto);
}
