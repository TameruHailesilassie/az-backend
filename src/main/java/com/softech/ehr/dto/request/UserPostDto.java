package com.softech.ehr.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softech.ehr.domain.entity.Role;
import com.softech.ehr.dto.response.AddressDto;
import com.softech.ehr.dto.response.DoctorsFeeDto;
import com.softech.ehr.dto.response.SalaryDto;
import com.softech.ehr.dto.response.SpecializationDto;
import com.softech.ehr.enums.Employment;
import com.softech.ehr.enums.Sex;
import com.softech.ehr.helpers.LocalDateDeserializer;
import com.softech.ehr.helpers.LocalDateSerializer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPostDto {

    private String firstName;
    private String middleName;
    private String lastName;
    private Sex sex;
    private String title;
    private String phoneNumber;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateStarted;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate contractDate;
    private Employment employment;
    private Set<Role> roles = new HashSet<>();
    private SalaryDto salary;
    private SpecializationDto specialization;
    private List<DoctorsFeeDto> doctorsFee;
    private AddressDto address;
}
