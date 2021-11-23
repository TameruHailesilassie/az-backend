package com.otlichno.ehr.configuration;

import com.otlichno.ehr.user.EmploymentType;
import com.otlichno.ehr.user.Role;
import com.otlichno.ehr.user.User;
import com.otlichno.ehr.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component

public class DataLoader implements ApplicationRunner {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (!userRepository.existsUserByEmail("admin@ehr.com")) {
            List<Role> roles = new ArrayList<>();
            roles.add(Role.ROLE_ADMIN);
            User adminUser = new User();
            adminUser.firstName(StringUtils.capitalize("Abebe"));
            adminUser.middleName(StringUtils.capitalize("Kebede"));
            adminUser.lastName(StringUtils.capitalize("Alemayehu"));
            adminUser.email("admin@ehr.com");
            adminUser.enabled(true);
            adminUser.password(passwordEncoder.encode("123456test"));
            adminUser.roles(roles);
            adminUser.employmentType(EmploymentType.PERMANENT);
            userRepository.save(adminUser);
        }
    }
}
