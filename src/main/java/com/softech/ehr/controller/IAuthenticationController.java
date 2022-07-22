package com.softech.ehr.controller;

import com.softech.ehr.dto.request.AuthPostDto;
import com.softech.ehr.dto.request.UserPostDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


public interface IAuthenticationController {


    @PostMapping("/login")
    ResponseEntity<?> authenticationRequest(
        @RequestBody @Valid AuthPostDto authenticationRequest,
        HttpServletResponse response);

    @GetMapping("/refresh")
    ResponseEntity<?> authenticationRequest(
        HttpServletRequest request, HttpServletResponse response);

    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody @Valid UserPostDto userPostDto);
}
