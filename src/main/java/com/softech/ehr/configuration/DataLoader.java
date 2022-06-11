package com.softech.ehr.configuration;

import com.softech.ehr.domain.entity.Address;
import com.softech.ehr.domain.entity.Role;
import com.softech.ehr.domain.entity.User;
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
import javax.transaction.Transactional;


@Component
public class DataLoader implements ApplicationRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(RoleRepository roleRepository,
                      UserRepository userRepository,
                      PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;


    }

    @Override
    @Transactional
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

        if (!userRepository.findByPhoneNumber("+251(900)-00-00-00")
            .isPresent()) {
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(
                roleRepository.findByName(RoleContainer.ADMIN).orElseThrow(
                    EntityNotFoundException::new));

            Set<Role> receptionistRole = new HashSet<>();
            receptionistRole.add(
                roleRepository.findByName(RoleContainer.RECEPTIONIST)
                    .orElseThrow(
                        EntityNotFoundException::new));

            User superAdmin = new User();
            superAdmin.setFirstName("girma");
            superAdmin.setMiddleName("Neshir");
            superAdmin.setLastName("kebede");
            superAdmin.setPhoneNumber("+251(900)-00-00-00");
            superAdmin.setPassword(this.passwordEncoder.encode("admin"));
            superAdmin.setRoles(adminRoles);

            superAdmin.addAddress(Address.builder()
                .email("admin@azEhr.com")
                .build());


            User receptionist = new User();
            receptionist.setFirstName("lidya");
            receptionist.setMiddleName("teklu");
            receptionist.setLastName("kebede");
            receptionist.setPhoneNumber("+251(901)-00-00-00");
            receptionist.setPassword(this.passwordEncoder.encode("azEhr"));
            receptionist.setRoles(receptionistRole);

            receptionist.addAddress(Address.builder()
                .email("lidu@azEhr.com")
                .build());
            userRepository.save(superAdmin);
            userRepository.save(receptionist);
        }
    }
}