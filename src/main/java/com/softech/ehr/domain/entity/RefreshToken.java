package com.softech.ehr.domain.entity;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.softech.ehr.domain.base.BaseEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken extends BaseEntity {
    @NotNull
    private String token;
    private Instant expires_at;
    private Instant last_used_at;
    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

}
