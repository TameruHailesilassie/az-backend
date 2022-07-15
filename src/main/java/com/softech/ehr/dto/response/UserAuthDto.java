package com.softech.ehr.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuthDto {

    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private List<RoleDto> roles;
}
