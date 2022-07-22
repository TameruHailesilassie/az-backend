package com.softech.ehr.controller.v1;

import com.softech.ehr.controller.BaseController;
import com.softech.ehr.controller.IAuthenticationController;
import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.AzUserMapper;
import com.softech.ehr.dto.request.AuthPostDto;
import com.softech.ehr.dto.request.UserPostDto;
import com.softech.ehr.dto.response.AuthDto;
import com.softech.ehr.dto.response.UserDto;
import com.softech.ehr.service.impl.AuthenticationServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/auth")
@Slf4j
public class AuthenticationControllerV1
    extends BaseController implements IAuthenticationController {
    private final String REFRESH_COOKIE = "refresh_token";
    private final AuthenticationServiceImpl authenticationService;
    private final AzUserMapper userMapper;


    @PostMapping("/login")
    public ResponseEntity<AuthDto> authenticationRequest(
        AuthPostDto authenticationRequest, HttpServletResponse response) {

        log.info("login request got");
        return ResponseEntity.ok(
            this.authenticationService.authenticate(authenticationRequest,
                response));
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthDto> authenticationRequest(
        HttpServletRequest request, HttpServletResponse response) {

        return ResponseEntity.ok(
            this.authenticationService.refreshToken(request, response));
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
