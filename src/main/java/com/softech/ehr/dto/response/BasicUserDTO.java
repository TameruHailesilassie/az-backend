package com.softech.ehr.dto.response;

import com.softech.ehr.domain.entity.Role;
import com.softech.ehr.domain.entity.Specialization;
import com.softech.ehr.enums.Employment;

import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasicUserDTO {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNUmber;
    private boolean enabled;
    private Employment employment;
    private String email;
    private Specialization specialization;
    @NotEmpty
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

}
