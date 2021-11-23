package com.otlichno.ehr.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_RECEPTION, ROLE_DOCTOR, ROLE_LAB_TECHNICIAN;

    @Override
    public String getAuthority() {
        return name();
    }


}
