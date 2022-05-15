package com.softech.ehr.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.softech.ehr.domain.entity.DoctorsCharge;
import com.softech.ehr.domain.entity.Role;
import com.softech.ehr.domain.entity.Salary;
import com.softech.ehr.domain.entity.Specialization;
import com.softech.ehr.enums.Employment;
import com.softech.ehr.enums.Sex;

import java.util.List;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasicUserDTO {

    private Long id;
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNUmber;
    private boolean enabled;
    private Salary salary;
    private Sex sex;
    private Employment employment;
    private String email;
    private List<DoctorsCharge> doctorsChargeList;
    private Specialization specialization;
    @NotEmpty
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

}
