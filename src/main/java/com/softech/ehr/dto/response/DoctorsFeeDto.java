package com.softech.ehr.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorsFeeDto {
    private Long id;
    private String name;
    private String value;

}
