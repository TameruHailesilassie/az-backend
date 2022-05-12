package com.softech.ehr.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.softech.ehr.dto.request.AuthenticationRequest;

@RequestMapping("api/auth")
public interface IAuthenticationController {


    @PostMapping("/login")
    ResponseEntity<?> authenticationRequest(@RequestBody @Valid AuthenticationRequest authenticationRequest);
    @GetMapping("/refresh")
    ResponseEntity<?> authenticationRequest(HttpServletRequest request);

    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody @Valid AuthenticationRequest authenticationRequest);
}
