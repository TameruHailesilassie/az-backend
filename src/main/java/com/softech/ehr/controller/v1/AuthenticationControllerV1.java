package com.softech.ehr.controller.v1;

import com.softech.ehr.controller.BaseController;
import com.softech.ehr.controller.IAuthenticationController;
import com.softech.ehr.dto.request.AuthenticationRequest;
import com.softech.ehr.dto.request.UserRegistrationDTO;
import com.softech.ehr.dto.response.AuthenticationResponse;
import com.softech.ehr.dto.response.BasicUserDTO;
import com.softech.ehr.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/auth")
public class AuthenticationControllerV1
    extends BaseController implements IAuthenticationController {


    private final AuthenticationService authenticationService;
    @Value("${ehr.token.header}")
    private String tokenHeader;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticationRequest(
        AuthenticationRequest authenticationRequest) {

        return ResponseEntity.ok(
            this.authenticationService.authenticate(authenticationRequest));
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> authenticationRequest(
        HttpServletRequest request) {
        return ResponseEntity.ok(this.authenticationService.refreshToken(
            request.getHeader(tokenHeader)));
    }

    @PostMapping("/register")
    public ResponseEntity<BasicUserDTO> registerUser(
        UserRegistrationDTO userRegistrationRequest) {
        return new ResponseEntity<>(
            this.authenticationService.registerUser(userRegistrationRequest),
            HttpStatus.CREATED);
    }


}
