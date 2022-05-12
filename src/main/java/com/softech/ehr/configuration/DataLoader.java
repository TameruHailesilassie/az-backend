package com.softech.ehr.configuration;

import com.softech.ehr.domain.entity.Role;
import com.softech.ehr.domain.entity.User;
import com.softech.ehr.enums.Employment;
import com.softech.ehr.repository.RoleRepository;
import com.softech.ehr.repository.UserRepository;
import com.softech.ehr.security.RoleContainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityNotFoundException;


@Component
public class DataLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleContainer roleContainer;

    @Autowired
    public DataLoader(RoleRepository roleRepository,
                      UserRepository userRepository,
                      PasswordEncoder passwordEncoder,
                      RoleContainer roleContainer) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleContainer = roleContainer;
    }

    @Override
    public void run(ApplicationArguments args) {

        if (!roleRepository.existsByName(RoleContainer.ADMIN)) {
            roleRepository.save(new Role(RoleContainer.ADMIN));
        }
        if (!roleRepository.existsByName(RoleContainer.PROVIDER)) {
            roleRepository.save(new Role(RoleContainer.PROVIDER));
        }
        if (!roleRepository.existsByName(RoleContainer.NURSE)) {
            roleRepository.save(new Role(RoleContainer.NURSE));
        }
        if (!roleRepository.existsByName(RoleContainer.LAB_TECHNICIAN)) {
            roleRepository.save(new Role(RoleContainer.LAB_TECHNICIAN));
        }
        if (!roleRepository.existsByName(RoleContainer.RADIOGRAPHER)) {
            roleRepository.save(new Role(RoleContainer.RADIOGRAPHER));
        }
        if (!roleRepository.existsByName(RoleContainer.RECEPTIONIST)) {
            roleRepository.save(new Role(RoleContainer.RECEPTIONIST));
        }

        if (!userRepository.findByEmail("admin@azEhr.com").isPresent()) {
            Set<Role> roles = new HashSet<>();
            roles.add(
                roleRepository.findByName(RoleContainer.ADMIN).orElseThrow(
                    EntityNotFoundException::new));

            User user = User.builder()
                .email("admin@azEhr.com")
                .firstName("Admin")
                .lastName("Admin")
                .password(this.passwordEncoder.encode("123456"))
                .enabled(true)
                .middleName("Admin")
                .phoneNumber("+251(924)-13-95-90")
                .employment(Employment.FULL_TIME)
                .roles(roles)
                .build();

            userRepository.save(user);
        }
    }
}