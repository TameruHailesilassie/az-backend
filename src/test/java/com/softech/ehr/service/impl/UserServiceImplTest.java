package com.softech.ehr.service.impl;

import com.github.javafaker.Faker;
import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.AzUserMapper;
import com.softech.ehr.dto.response.UserDto;
import com.softech.ehr.enums.Employment;
import com.softech.ehr.enums.Sex;
import com.softech.ehr.exception.NoUserFoundException;
import com.softech.ehr.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    private static final String phoneNumber = "+251924139590";
    private static final Pageable pr = PageRequest.of(0, 1);

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private AzUserMapper userMapper;

    private Faker faker;

    @Autowired
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }

    @Test
    void shouldFetchPagedAllUsers() {
        //given
        when(this.userRepo.findAll(pr))
            .thenReturn(
                new PageImpl<>(Collections.singletonList(createFakeUser()), pr,
                    1L));

        //when
        Page<UserDto> result = userService.getAllUsers(0, 1);
        verify(userRepo).findAll(pr);
        //Then
        assert (result.getContent().size() == 1);
        assertNotNull(result);
    }

    @Test
    void shouldFetchUserByPhoneNumber() {
        //given
        User user = new User();
        user.setFirstName(faker.name().firstName());
        user.setMiddleName(faker.name().lastName());
        user.setLastName(faker.name().firstName());
        user.setPhoneNumber(phoneNumber);
        user.setEnabled(true);
        user.setEmployment(Employment.FULL_TIME);
        user.setTitle(faker.name().title());
        user.setSex(Sex.MALE);
        user.setDateStarted(LocalDate.now());

        given(this.userRepo.findByPhoneNumber(any()))
            .willReturn(Optional.of(user));
        //when
        UserDto userReturned = userService.getUserByPhoneNumber(any());
        //then
        verify(this.userRepo, times(1)).findByPhoneNumber(any());
        assertEquals(userReturned.getPhoneNumber(), user.getPhoneNumber());
    }

    @Test
    void shouldThrowNoUserFoundException() {

        //given
        when(userRepo.findByPhoneNumber(any()))
            .thenReturn(Optional.empty());

        //when
        NoUserFoundException thrown =
            assertThrows(NoUserFoundException.class, () -> {
                userService.getUserByPhoneNumber(any());
            });
        //then
        assertEquals("No user found with phoneNumber null.",
            thrown.getMessage());
        verify(userRepo).findByPhoneNumber(any());
    }

    private User createFakeUser() {
        User user = new User();
        user.setFirstName(faker.name().firstName());
        user.setMiddleName(faker.name().lastName());
        user.setLastName(faker.name().firstName());
        user.setPhoneNumber(phoneNumber);
        user.setEnabled(true);
        user.setEmployment(Employment.FULL_TIME);
        user.setTitle(faker.name().title());
        user.setSex(Sex.MALE);
        user.setDateStarted(LocalDate.now());

        /*fakeUser.setAddress(
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

        fakeUser.setSalary(
            Salary.builder()
                .type(SalaryType.NET)
                .amount(BigDecimal.valueOf(3800))
                .build());*/
        return user;
    }


}