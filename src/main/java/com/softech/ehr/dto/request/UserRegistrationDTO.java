package com.softech.ehr.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softech.ehr.domain.entity.DoctorsCharge;
import com.softech.ehr.enums.Employment;
import com.softech.ehr.enums.SalaryType;
import com.softech.ehr.enums.Sex;
import com.softech.ehr.helpers.LocalDateDeserializer;
import com.softech.ehr.helpers.LocalDateSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {

    private String firstName;
    private String middleName;
    private String lastName;
    private Sex sex;
    private String title;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    // @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "d-MMM-yyyy")
    private LocalDate dateStarted;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "d-MMM-yyyy")
    private LocalDate contractDate;
    private Employment employment;
    private String email;
    private String phoneNumber;
    private Set<String> roles = new HashSet<>();
    private BigDecimal salaryAmount;
    private SalaryType salaryType;
    private Long specialization;
    private List<DoctorsCharge> doctorsCharge;
}
