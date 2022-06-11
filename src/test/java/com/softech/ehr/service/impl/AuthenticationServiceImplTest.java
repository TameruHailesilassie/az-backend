package com.softech.ehr.service.impl;

import com.softech.ehr.domain.entity.Address;
import com.softech.ehr.domain.entity.Salary;
import com.softech.ehr.domain.entity.Specialization;
import com.softech.ehr.domain.entity.User;
import com.softech.ehr.enums.Employment;
import com.softech.ehr.enums.SalaryType;
import com.softech.ehr.enums.Sex;
import com.softech.ehr.exception.UserAlreadyExistException;
import com.softech.ehr.repository.UserRepository;
import com.softech.ehr.security.TokenUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

@ContextConfiguration(classes = {AuthenticationServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AuthenticationServiceImplTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationServiceImpl authenticationServiceImpl;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private TokenUtils tokenUtils;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void shouldRegisterUser() {

        //given
        User testUser = createFakeUser();
        when(this.userRepository.existsByPhoneNumber(any()))
            .thenReturn(false);

        when(this.passwordEncoder.encode(any()))
            .thenReturn("Secrete");

        when(this.userRepository.save(any()))
            .thenReturn(testUser);
        //when
        User savedUser = this.authenticationServiceImpl.registerUser(testUser);
        //then
        verify(this.userRepository).existsByPhoneNumber(any());
        verify(this.passwordEncoder).encode(any());
        assertNotNull(savedUser);
        assertSame(savedUser, testUser);

    }

    private User createFakeUser() {
        Salary salary = new Salary();
        salary.setRemark("Remark");
        salary.setAmount(BigDecimal.valueOf(42L));
        salary.setUser(new User());
        salary.setType(SalaryType.GROSS);

        Specialization specialization = new Specialization();
        specialization.name("Name");
        specialization.users(null);

        Address address = new Address();
        address.setEmail("jane.doe@example.org");
        address.setKebele("Kebele");
        address.setSubCity("Sub City");
        address.setCountry("GB");
        address.setWereda("Wereda");
        address.setUser(new User());
        address.setCity("Oxford");
        address.setResident_phone("4105551212");
        address.setPhoneNumberTwo("4105551212");
        address.setHouseNumber("42");

        User user = new User();
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setCreatedDate(null);
        user.setContractDate(null);
        user.setSalary(salary);
        user.setDoctorsFee(null);
        user.setEnabled(true);
        user.setPhoneNumber("4105551212");
        user.setSpecialization(specialization);
        user.setMiddleName("Middle Name");
        user.setAddress(address);
        user.setFirstName("Jane");
        user.setLastModifiedDate(null);
        user.setEmployment(Employment.PART_TIME);
        user.setSex(Sex.MALE);
        user.setTitle("Dr");
        user.setDateStarted(null);
        user.setProfileImage("AAAAAAAA".getBytes(StandardCharsets.UTF_8));
        user.setRoles(null);
        return user;
    }

    @Test
    void shouldThrowUserAlreadyExistException() {
        //given
        when(this.userRepository.existsByPhoneNumber(any()))
            .thenReturn(true);
        //then
        assertThrows(UserAlreadyExistException.class, () ->
            this.authenticationServiceImpl.registerUser(createFakeUser()));

        verify(this.userRepository).existsByPhoneNumber(any());
    }
}

