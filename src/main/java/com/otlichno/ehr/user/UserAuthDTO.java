package com.otlichno.ehr.user;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UserAuthDTO {
    @Email
    private String email;
    private String password;
}
