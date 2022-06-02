package com.softech.ehr.domain.entity;

import com.softech.ehr.domain.base.BaseEntity;
import com.softech.ehr.enums.Employment;
import com.softech.ehr.enums.Sex;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

public class User extends BaseEntity {

    @OneToMany(mappedBy = "user"
        , cascade = CascadeType.ALL
        , orphanRemoval = true)
    List<DoctorsCharge> doctorsFee = new ArrayList<DoctorsCharge>();
    @NotBlank(message = "first name can not be blank")
    private String firstName;
    private String title;
    @Lob
    private byte[] profileImage;
    @NotBlank(message = "middle name can not be blank")
    private String middleName;
    @NotBlank(message = "last  name can not be blank")
    private String lastName;
    private Sex sex;
    @CreatedDate
    private LocalDateTime createdDate;
    private LocalDate dateStarted;
    private LocalDate contractDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    private Employment employment;

    @NotEmpty
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    @NotBlank(message = "password can not be blank")
    private String password;
    @NotNull(message = "Enable should be set")
    private boolean enabled;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "long", name = "specialization_id")
    private Specialization specialization;
    @NotBlank(message = "phone number can not be blank")
    private String phoneNumber;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Salary salary;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Address address;

    public void addDoctorsCharge(DoctorsCharge doctorsCharge) {
        doctorsFee.add(doctorsCharge);
        doctorsCharge.user(this);
    }

    public void removeDoctorsCharge(DoctorsCharge doctorsCharge) {
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
