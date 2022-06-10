package com.softech.ehr.dto;

import com.softech.ehr.domain.entity.DoctorsFee;
import com.softech.ehr.domain.entity.Salary;
import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.request.UserPostDto;
import com.softech.ehr.dto.response.DoctorsFeeDto;
import com.softech.ehr.dto.response.SalaryDto;
import com.softech.ehr.dto.response.UserDto;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {AzModelMapper.class},
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface AzUserMapper {

    User toUser(UserPostDto userPostDto);

    UserDto toUserDto(User user);

    DoctorsFeeDto toDoctorsFeeDto(DoctorsFee doctorsFee);

    DoctorsFee toDoctorsFee(DoctorsFeeDto doctorsFeeDto);

    SalaryDto toSalaryDto(Salary salary);

    Salary toSalary(SalaryDto salaryDto);

}
