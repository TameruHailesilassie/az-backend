package com.softech.ehr.user;

import com.softech.ehr.dto.LoginDTO;
import com.softech.ehr.dto.LoginResponseDTO;
import com.softech.ehr.dto.UserDTO;
import com.softech.ehr.security.AuthService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Collection;
import javax.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/users")
@Api(value = "User Service", tags = "User service end points")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private AuthService authService;

    @ApiOperation(value = "fetch User By Email", response = User.class)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{email}")
    public User getUserByEmail(
        @ApiParam(value = "user Email") @PathVariable("email") String email) {
        return userService.fetchUserByEmail(email);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Fetch All Users", response = Collection.class)
    public Collection<UserDTO> fetchAllUsers() {
        return userService.fetchAllUsers();
    }

    @GetMapping("/test")
    @ApiOperation(value = "test case", response = Collection.class)
    public String test() {
        return "hi";
    }


    @PostMapping("/login")
    @ApiOperation(value = "user Login end Point to get Access token", response = String.class)
    public LoginResponseDTO login(
        @Valid @RequestBody @ApiParam(value = "user email and Password")
            LoginDTO login) {
        return authService.authenticate(login);
    }

}
