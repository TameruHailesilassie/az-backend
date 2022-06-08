package com.softech.ehr.dto;

import com.softech.ehr.domain.entity.Address;
import com.softech.ehr.domain.entity.DoctorsFee;
import com.softech.ehr.domain.entity.Salary;
import com.softech.ehr.domain.entity.Specialization;
import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.request.UserPostDto;
import com.softech.ehr.dto.response.AddressDto;
import com.softech.ehr.dto.response.DoctorsFeeDto;
import com.softech.ehr.dto.response.SalaryDto;
import com.softech.ehr.dto.response.SpecializationDto;
import com.softech.ehr.dto.response.UserDto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AzModelMapper {

    UserDto toUserDto(User user);

    AddressDto toAddressDto(Address address);

    DoctorsFeeDto toDoctorsFeeDto(DoctorsFee doctorsFee);

    SalaryDto toSalaryDto(Salary salary);

    SpecializationDto toSpecializationDto(Specialization specialization);

    User toUser(UserPostDto userPostDto);

}
