package com.softech.ehr.domain.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.softech.ehr.domain.base.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    @JsonProperty
    private String phoneNumberTwo;
    @Email(message = "Please use a valid email")
    @NotBlank(message = "email can not be empty")
    @JsonProperty
    private String email;
    @JsonProperty
    private String city;
    @JsonProperty
    private String subCity;
    @JsonProperty
    private String country;
    @JsonProperty
    private String wereda;
    @JsonProperty
    private String kebele;
    @JsonProperty
    private String houseNumber;
    @JsonProperty
    private String resident_phone;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

}
