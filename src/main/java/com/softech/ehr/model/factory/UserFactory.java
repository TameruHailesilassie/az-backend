package com.softech.ehr.model.factory;

import com.softech.ehr.domain.entity.Role;
import com.softech.ehr.domain.entity.User;
import com.softech.ehr.model.security.SecurityUser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserFactory {

    public static SecurityUser create(User user) {
        return new SecurityUser(
            user.getId(),
            user.getPhoneNumber(),
            user.getPassword(),
            user.getAddress().getEmail(),
            null,
            createAuthoritiesToUser(user.getRoles())
        );
    }

    public static Collection<? extends GrantedAuthority> createAuthoritiesToUser(
        Set<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities =
            new HashSet<>(roles.size());

        for (Role role : roles) {
            authorities.add(
                new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        return authorities;
    }

}
