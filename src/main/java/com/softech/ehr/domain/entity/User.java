package com.softech.ehr.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.softech.ehr.domain.base.BaseEntity;
import com.softech.ehr.enums.Employment;
import com.softech.ehr.enums.Sex;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javax.persistence.Table;
import javax.validation.constraints.Email;
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
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(fluent = true)
@EnableJpaAuditing

public class User extends BaseEntity {

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<DoctorsCharge> doctorsCharge;
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
    @NotBlank(message = "phone number can not be blank")
    private String phoneNumber;
    @Email(message = "Please use a valid email")
    @NotBlank(message = "email can not be empty")
    private String email;
    @NotEmpty
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "salary_id")
    private Salary salary;
    @NotBlank(message = "password can not be blank")
    private String password;
    @NotNull(message = "Enable should be set")
    private boolean enabled;
    @ManyToOne
    @JoinColumn(columnDefinition = "long", name = "specialization_id")
    private Specialization specialization;

}
