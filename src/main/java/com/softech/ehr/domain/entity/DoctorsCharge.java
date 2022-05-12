package com.softech.ehr.domain.entity;

import com.softech.ehr.domain.base.BaseEntity;

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
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(fluent = true)
@EnableJpaAuditing
public class DoctorsCharge extends BaseEntity {

    private BigDecimal visitFee;
    private BigDecimal consultationFee;

    @OneToOne(mappedBy = "doctorsCharge")
    private User user;
}
