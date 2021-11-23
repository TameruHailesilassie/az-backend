package com.otlichno.ehr.user;

import com.otlichno.ehr.security.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

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
    public User getUserByEmail(@ApiParam(value = "user Email") @PathVariable("email") String email) {
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
    public String  test() {
        return "hi";
    }
    @PostMapping("/login")
    @ApiOperation(value = "user Login end Point to get Access token", response = String.class)
    public String login(@Valid @RequestBody @ApiParam(value = "user email and Password") UserAuthDTO userAuthDTO) {
        System.out.println(userAuthDTO.getEmail());
        System.out.println(userAuthDTO.getPassword());
        return authService.authenticate(userAuthDTO);
    }

}
