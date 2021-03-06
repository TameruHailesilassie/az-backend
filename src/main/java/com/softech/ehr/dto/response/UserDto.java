package com.softech.ehr.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softech.ehr.domain.entity.Role;
import com.softech.ehr.enums.Employment;
import com.softech.ehr.enums.Sex;
import com.softech.ehr.helpers.LocalDateDeserializer;
import com.softech.ehr.helpers.LocalDateSerializer;

import java.time.LocalDate;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UserDto {

    private Long id;
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private boolean enabled;
    private SalaryDto salary;
    private Sex sex;
    private Employment employment;
    private Set<DoctorsFeeDto> doctorsFee;
    private SpecializationDto specialization;
    private AddressDto address;
    private String phoneNumber;
    private Set<Role> roles;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateStarted;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate contractDate;

}
