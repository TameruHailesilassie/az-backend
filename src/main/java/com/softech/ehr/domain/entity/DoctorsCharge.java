package com.softech.ehr.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.softech.ehr.domain.base.BaseEntity;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.Entity;
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
@Accessors(fluent = true)
@EnableJpaAuditing
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorsCharge extends BaseEntity {

    @JsonProperty
    private String name;
    @JsonProperty
    private String value;

    @ManyToOne
    @JoinColumn(columnDefinition = "long", name = "user_id")
    private User user;

}
