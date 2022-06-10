package com.softech.ehr.dto;

import com.softech.ehr.domain.entity.Address;
import com.softech.ehr.domain.entity.Specialization;
import com.softech.ehr.dto.response.AddressDto;
import com.softech.ehr.dto.response.SpecializationDto;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface AzModelMapper {

    AddressDto toAddressDto(Address address);

    Address toAddress(AddressDto addressDto);

    SpecializationDto toSpecializationDto(Specialization specialization);

}
