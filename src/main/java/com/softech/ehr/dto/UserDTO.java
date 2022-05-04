package com.softech.ehr.dto;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.NotEmpty;
import java.util.List;

import com.softech.ehr.user.EmploymentType;
import com.softech.ehr.user.Role;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private boolean enabled;
    private EmploymentType employmentType;
    private String email;
    @NotEmpty
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;
}
