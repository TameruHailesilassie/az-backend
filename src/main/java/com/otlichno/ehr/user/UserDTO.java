package com.otlichno.ehr.user;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.NotEmpty;
import java.util.List;
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
