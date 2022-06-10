package com.softech.ehr.domain.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softech.ehr.domain.base.BaseEntity;
import com.softech.ehr.enums.Employment;
import com.softech.ehr.enums.Sex;
import com.softech.ehr.helpers.LocalDateDeserializer;
import com.softech.ehr.helpers.LocalDateSerializer;

import org.apache.commons.lang3.StringUtils;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @OneToMany(mappedBy = "user"
        , cascade = CascadeType.ALL
        , orphanRemoval = true)
    List<DoctorsFee> doctorsFee;

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
    // @Builder.Default
    private boolean enabled = true;
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

    public void addDoctorsFee(DoctorsFee doctorsFee) {

        if (this.doctorsFee == null) {
            this.doctorsFee = new ArrayList<>();
        }
        this.doctorsFee.add(doctorsFee);
        doctorsFee.setUser(this);
        System.out.printf("add doctors fee called with name %s", doctorsFee.getName());
    }

    public void removeDoctorsCharge(DoctorsFee doctorsCharge) {
        doctorsFee.remove(doctorsCharge);
        doctorsCharge.setUser(null);
    }

    public void addAddress(Address address) {
        if (address != null) {
            address.setUser(this);
            this.address = address;
        }

    }

    public void addSalary(Salary salary) {
        if (salary != null) {
            this.salary = salary;
            salary.setUser(this);
        }
    }

    public void setFirstName(String firstName) {
        this.firstName = StringUtils.capitalize(firstName);
    }

    public void setMiddleName(String middleName) {
        this.middleName = StringUtils.capitalize(middleName);
    }

    public void setLastName(String lastName) {
        this.lastName = StringUtils.capitalize(lastName);
    }
}
