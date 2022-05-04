package com.softech.ehr.dto;

import java.util.List;
import java.util.Set;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;

import com.softech.ehr.user.Role;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String firstName;
    private String middleNae;
    private String lastName;
    @Email
    private String email;
    private String token;
    private List<Role> roles;


}
