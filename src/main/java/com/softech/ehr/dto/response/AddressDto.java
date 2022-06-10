package com.softech.ehr.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {
    private String phoneNumberTwo;
    private String email;
    private String city;
    private String subCity;
    private String country;
    private String wereda;
    private String kebele;
    private String resident_phone;
    private String houseNumber;


}
