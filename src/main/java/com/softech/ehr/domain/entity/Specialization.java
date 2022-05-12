package com.softech.ehr.domain.entity;

import com.softech.ehr.domain.base.BaseEntity;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
@EnableJpaAuditing
public class Specialization extends BaseEntity {
    @OneToMany(mappedBy = "specialization", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    List<User> users;
    @NotNull
    private String name;
}
