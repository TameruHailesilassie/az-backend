package com.softech.ehr.domain.entity;

import com.softech.ehr.domain.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(fluent = true)

public class Address extends BaseEntity {

    private String phoneNumberTwo;
    @Email(message = "Please use a valid email")
    @NotBlank(message = "email can not be empty")
    private String email;
    private String city;
    private String subCity;
    private String country;
    private String wereda;
    private String kebele;
    private String houseNumber;
    private String resident_phone;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

}
