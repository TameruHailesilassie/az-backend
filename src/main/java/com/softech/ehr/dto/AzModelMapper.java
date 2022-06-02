package com.softech.ehr.dto;

import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.response.BasicUserDTO;
import com.softech.ehr.helpers.AzDateFormatter;

import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class AzModelMapper {

    public BasicUserDTO convertToUserDto(User user) {

        return new BasicUserDTO(
            user.getId(),
            user.title(),
            user.firstName(),
            user.middleName(),
            user.lastName(),
            user.enabled(),
            user.salary(),
            user.sex(),
            user.employment(),
            user.doctorsFee(),
            user.specialization(),
            user.address(),
            user.phoneNumber(),
            user.roles(),
            AzDateFormatter.formatLocalDate(user.dateStarted()),
            AzDateFormatter.formatLocalDate(user.contractDate())

        );
    }
}
