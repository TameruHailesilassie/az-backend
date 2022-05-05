package com.softech.ehr.dto;

import com.softech.ehr.role.Role;

import java.util.Set;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String firstName;
    private String middleNae;
    private String lastName;
    @Email
    private String email;
    private String token;
    private Set<Role> roles;


}
