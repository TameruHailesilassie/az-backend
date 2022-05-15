package com.softech.ehr.dto;

import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.response.BasicUserDTO;

import org.springframework.stereotype.Component;

@Component
public class EhrModelMapper {

    public BasicUserDTO convertToUserDto(User user) {

        return new BasicUserDTO(
            user.getId(),
            user.title(),
            user.firstName(),
            user.middleName(),
            user.lastName(),
            user.phoneNumber(),
            user.enabled(),
            user.salary(),
            user.sex(),
            user.employment(),
            user.email(),
            user.doctorsCharge(),
            user.specialization(),
            user.roles()
        );
    }
}
