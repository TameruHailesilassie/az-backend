package com.softech.ehr.dto.response;

import com.softech.ehr.domain.entity.Role;
import com.softech.ehr.model.base.ModelBase;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthenticationResponse extends ModelBase {

    private static final long serialVersionUID = 7431193836933783650L;
    Set<Role> roles;
    private String userName;
    private String firstName;
    private String middleName;
    private String accessToken;
}
