package com.softech.ehr.dto;

import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.response.BasicUserDTO;
import com.softech.ehr.exception.EntityNotFoundException;
import com.softech.ehr.repository.SalaryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class EhrModelMapper {
    private SalaryRepository salaryRepository;

    @Autowired
    public EhrModelMapper(
        SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    public BasicUserDTO convertToUserDto(User user) {

        if (salaryRepository == null) {
            System.out.println("Salary Repo null");
        }
        return new BasicUserDTO(
            user.getId(),
            user.title(),
            user.firstName(),
            user.middleName(),
            user.lastName(),
            user.phoneNumber(),
            user.enabled(),
            salaryRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException(
                    "Salary not found for User " + user.firstName())),
            user.sex(),
            user.employment(),
            user.email(),
            user.doctorsFee(),
            user.specialization(),
            user.roles()
        );
    }
}
