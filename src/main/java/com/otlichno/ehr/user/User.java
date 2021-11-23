package com.otlichno.ehr.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@Accessors(fluent = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "first name can not be blank")
    private String firstName;

    @NotBlank(message = "middle name can not be blank")
    private String middleName;

    @NotBlank(message = "last  name can not be blank")
    private String lastName;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @NotNull(message = "Enable should be set")
    private boolean enabled;

    @NotBlank(message = "password can not be blank")
    private String password;
    @NotNull
    private EmploymentType employmentType;
    @Email(message = "Please use a valid email")
    @NotBlank(message = "email can not be empty")
    private String email;
    @NotEmpty
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;
}
