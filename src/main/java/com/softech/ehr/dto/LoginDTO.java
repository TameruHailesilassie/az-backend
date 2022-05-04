package com.softech.ehr.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginDTO {
    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message="Password is Mandatory")
    private String password;

}
