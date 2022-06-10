package com.softech.ehr.controller.v1;

import com.softech.ehr.dto.AzUserMapper;
import com.softech.ehr.controller.BaseController;
import com.softech.ehr.controller.IAuthenticationController;
import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.AzModelMapper;
import com.softech.ehr.dto.request.AuthPostDto;
import com.softech.ehr.dto.request.UserPostDto;
import com.softech.ehr.dto.response.AuthDto;
import com.softech.ehr.dto.response.UserDto;
import com.softech.ehr.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final AzUserMapper userMapper;
    @Value("${ehr.token.header}")
    private String tokenHeader;




    @PostMapping("/login")
    public ResponseEntity<AuthDto> authenticationRequest(
        AuthPostDto authenticationRequest) {

        return ResponseEntity.ok(
            this.authenticationService.authenticate(authenticationRequest));
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthDto> authenticationRequest(
        HttpServletRequest request) {
        return ResponseEntity.ok(this.authenticationService.refreshToken(
            request.getHeader(tokenHeader)));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(
        @RequestBody UserPostDto postDto) {
        User user = this.authenticationService
            .registerUser(userMapper.toUser(postDto));
        return new ResponseEntity<>(userMapper.toUserDto(user),
            HttpStatus.CREATED);
    }


}
