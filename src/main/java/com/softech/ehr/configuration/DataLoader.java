package com.softech.ehr.configuration;

import com.github.javafaker.Faker;
import com.softech.ehr.domain.entity.Address;
import com.softech.ehr.domain.entity.Role;
import com.softech.ehr.domain.entity.Salary;
import com.softech.ehr.domain.entity.User;
import com.softech.ehr.enums.Employment;
import com.softech.ehr.enums.SalaryType;
import com.softech.ehr.enums.Sex;
import com.softech.ehr.repository.RoleRepository;
import com.softech.ehr.repository.UserRepository;
import com.softech.ehr.security.RoleContainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;


@Component
public class DataLoader implements ApplicationRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Faker faker;

    @Autowired
    public DataLoader(RoleRepository roleRepository,
                      UserRepository userRepository,
                      PasswordEncoder passwordEncoder,
                      Faker faker) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        this.faker = faker;
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

        if (!userRepository.findByPhoneNumber("+251(924)-13-95-90")
            .isPresent()) {
            Set<Role> roles = new HashSet<>();
            roles.add(
                roleRepository.findByName(RoleContainer.ADMIN).orElseThrow(
                    EntityNotFoundException::new));

          /*  User superAdmin = User.builder()
                .firstName("Admin")
                .lastName("Admin")
                .phoneNumber("+251(924)-13-95-90")
                .password(this.passwordEncoder.encode("123456"))
                .enabled(true)
                .middleName("Admin")
                .employment(Employment.FULL_TIME)
                .roles(roles)
                .build();

          /*  superAdmin.addAddress(Address.builder()
                .email("admin@azEhr.com")
                .build());
            userRepository.save(superAdmin);*/

        }
/*
        //Sample Fake user Data
        List<User> fakeUsers = IntStream.rangeClosed(1, 10)
            .mapToObj(i -> {
                Set<Role> fakeRoles = new HashSet<>();
                fakeRoles.add(
                    roleRepository.findByName(RoleContainer.PROVIDER)
                        .orElseThrow(
                            EntityNotFoundException::new));

                User fakeUser = User.builder()
                    .firstName(faker.name().firstName())
                    .middleName(faker.name().lastName())
                    .lastName(faker.name().firstName())
                    .phoneNumber(faker.phoneNumber().cellPhone())
                    .password(this.passwordEncoder.encode(
                        faker.internet().password()))
                    .enabled(true)
                    .employment(Employment.FULL_TIME)
                    .roles(fakeRoles)
                    .title(faker.name().title())
                    .sex(Sex.MALE)
                    .dateStarted(LocalDate.now())
                    .build();

                fakeUser.addAddress(
                    Address.builder()
                        .email(faker.internet().emailAddress())
                        .city(faker.address().city())
                        .country(faker.address().country())
                        .phoneNumberTwo(faker.phoneNumber().cellPhone())
                        .subCity(faker.address().state())
                        .wereda(faker.address().streetName())
                        .kebele(faker.address().streetAddressNumber())
                        .houseNumber(faker.address().buildingNumber())
                        .resident_phone(
                            faker.phoneNumber().subscriberNumber())
                        .build());

                fakeUser.addSalary(
                    Salary.builder()
                        .type(SalaryType.NET)
                        .amount(BigDecimal.valueOf(3800))
                        .build());
                return fakeUser;
            }).collect(Collectors.toList());
*/
       // userRepository.saveAll(fakeUsers);
    }
}