package com.softech.ehr.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softech.ehr.domain.base.BaseEntity;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@EnableJpaAuditing

public class DoctorsFee extends BaseEntity {

    @JsonProperty
    private String name;
    @JsonProperty
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "long", name = "user_id")
    private User user;

}
