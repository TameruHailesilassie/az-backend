package com.softech.ehr.domain.entity;

import com.softech.ehr.domain.base.BaseEntity;
import com.softech.ehr.enums.SalaryType;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "salary")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(fluent = true)
@EnableJpaAuditing
public class Salary extends BaseEntity {

    @NotNull(message = "please enter salary amount")
    private BigDecimal amount;
    @NotNull(message = "Please choose Salary Type")
    private SalaryType type;

    private String remark;
    @OneToOne(mappedBy = "salary")
    private User user;


}
