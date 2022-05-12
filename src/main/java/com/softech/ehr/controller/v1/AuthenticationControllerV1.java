package com.softech.ehr.controller.v1;

import com.softech.ehr.controller.BaseController;
import com.softech.ehr.controller.IAuthenticationController;
import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.request.AuthenticationRequest;
import com.softech.ehr.dto.response.AuthenticationResponse;
import com.softech.ehr.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthenticationControllerV1 extends BaseController implements
    IAuthenticationController {


    private final AuthenticationService authenticationService;
    @Value("${ehr.token.header}")
    private String tokenHeader;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticationRequest(
        AuthenticationRequest authenticationRequest) {

        return ResponseEntity.ok(
            this.authenticationService.authenticate(authenticationRequest));
    }

    public ResponseEntity<AuthenticationResponse> authenticationRequest(
        HttpServletRequest request) {
        return ResponseEntity.ok(this.authenticationService.refreshToken(
            request.getHeader(tokenHeader)));
    }

    public ResponseEntity<User> registerUser(
        AuthenticationRequest authenticationRequest) {
        return new ResponseEntity<>(
            this.authenticationService.registerUser(authenticationRequest),
            HttpStatus.CREATED);
    }

}
