package com.softech.ehr.domain.entity;

import com.softech.ehr.domain.base.BaseEntity;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
    @OneToMany(mappedBy = "specialization", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<User> users= new ArrayList<>();
    private String name;

    public void addUser(User user) {
        this.users.add(user);
        user.setSpecialization(this);
    }
}
