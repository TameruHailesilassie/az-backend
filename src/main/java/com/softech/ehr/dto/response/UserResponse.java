package com.softech.ehr.dto.response;

import com.softech.ehr.domain.entity.Role;
import com.softech.ehr.enums.Employment;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNUmber;
    private boolean enabled;
    private Employment employment;
    private String email;
    @NotEmpty
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;
}
