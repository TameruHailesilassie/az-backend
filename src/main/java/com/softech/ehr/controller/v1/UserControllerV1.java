package com.softech.ehr.controller.v1;

import com.softech.ehr.controller.BaseController;
import com.softech.ehr.controller.IUserController;
import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.response.BasicUserDTO;
import com.softech.ehr.exception.NoUserFoundException;
import com.softech.ehr.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserControllerV1 extends BaseController implements
    IUserController {

    private final UserService userService;

    @GetMapping()
    @PreAuthorize("hasRole(@R.ADMIN)")
    public ResponseEntity<List<BasicUserDTO>> getAllUsers() {
        logger.debug("attempt to fetch all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByName(
        @PathVariable("email") String email) {
        User aUser = userService.getUserByEmail(email)
            .orElseThrow(() -> new NoUserFoundException(email));
        return ResponseEntity.ok(aUser);
    }

    @GetMapping("/test")
    public String testUserController() {
        return "User Controller is working!";
    }
}
