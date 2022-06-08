package com.softech.ehr.domain.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softech.ehr.domain.base.BaseEntity;
import com.softech.ehr.enums.Employment;
import com.softech.ehr.enums.Sex;
import com.softech.ehr.helpers.LocalDateDeserializer;
import com.softech.ehr.helpers.LocalDateSerializer;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User extends BaseEntity {

    @OneToMany(mappedBy = "user"
        , cascade = CascadeType.ALL
        , orphanRemoval = true)
    List<DoctorsFee> doctorsFee = new ArrayList<DoctorsFee>();

    private String firstName;

    private String title;
    @Lob
    private byte[] profileImage;

    private String middleName;

    private String lastName;
    private Sex sex;
    @CreatedDate
    private LocalDateTime createdDate;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateStarted;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate contractDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    private Employment employment;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    private String password;

    private boolean enabled;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "long", name = "specialization_id")
    private Specialization specialization;

    private String phoneNumber;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Salary salary;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Address address;

    public void addDoctorsCharge(DoctorsFee doctorsCharge) {
        //doctorsFee.add(doctorsCharge);
        doctorsCharge.user(this);
    }

    public void removeDoctorsCharge(DoctorsFee doctorsCharge) {
        doctorsFee.remove(doctorsCharge);
        doctorsCharge.user(null);
    }

    public void addAddress(Address address) {
        if (address != null) {
            this.address = address;
            address.user(this);
        }

    }
    public void addSalary(Salary salary) {
        if (salary != null) {
            this.salary = salary;
            salary.user(this);
        }

    }

}
