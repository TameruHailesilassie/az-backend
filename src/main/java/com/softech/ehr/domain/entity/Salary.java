package com.softech.ehr.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.softech.ehr.domain.base.BaseEntity;
import com.softech.ehr.enums.SalaryType;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Salary extends BaseEntity {

    @JsonProperty
    private BigDecimal amount;
    @JsonProperty
    private SalaryType type;
    @JsonProperty
    private String remark;
    @OneToOne(mappedBy = "salary")
    private User user;

}
