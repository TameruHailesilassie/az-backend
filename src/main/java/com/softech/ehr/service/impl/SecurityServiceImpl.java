package com.softech.ehr.service.impl;

import com.softech.ehr.service.ISecurityService;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements ISecurityService {

    @Override
    public Boolean hasProtectedAccess() {
        return (SecurityContextHolder.getContext().getAuthentication()
            .getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")));
    }

}
