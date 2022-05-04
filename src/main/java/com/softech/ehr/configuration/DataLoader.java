package com.softech.ehr.configuration;


import com.softech.com.trackerbackend.Role.Role;
import com.softech.com.trackerbackend.Role.RoleName;
import com.softech.com.trackerbackend.Role.RoleRepository;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component

public class DataLoader implements ApplicationRunner {
    private RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) {

        if (!roleRepository.existsByName(RoleName.ROLE_ADMIN)) {
            roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        }
        if (!roleRepository.existsByName(RoleName.ROLE_WAITER)) {
            roleRepository.save(new Role(RoleName.ROLE_WAITER));
        }
        if (!roleRepository.existsByName(RoleName.ROLE_F_B)) {
            roleRepository.save(new Role(RoleName.ROLE_F_B));
        }
        if (!roleRepository.existsByName(RoleName.ROLE_STORE_KEEPER)) {
            roleRepository.save(new Role(RoleName.ROLE_STORE_KEEPER));
        }

    }
}
