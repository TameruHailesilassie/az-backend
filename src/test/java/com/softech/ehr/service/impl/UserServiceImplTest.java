package com.softech.ehr.service.impl;

import com.github.javafaker.Faker;
import com.softech.ehr.domain.entity.Address;
import com.softech.ehr.domain.entity.Salary;
import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.AzModelMapper;
import com.softech.ehr.dto.response.UserDto;
import com.softech.ehr.enums.Employment;
import com.softech.ehr.enums.SalaryType;
import com.softech.ehr.enums.Sex;
import com.softech.ehr.exception.NoUserFoundException;
import com.softech.ehr.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final String phoneNumber = "+251924139590";
    private static final Pageable pr = PageRequest.of(0, 1);
    private UserServiceImpl userService;
    private UserRepository userRepo;
    private Faker faker;
    private User fakeUser;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
        fakeUser = createFakeUser();
        userRepo = createUserRepository();
        userService = new UserServiceImpl(userRepo, createModelMapper());

    }

    @Test
    void shouldFetchPagedAllUsers() {
        //given
        when(this.userRepo.findAll(pr))
            .thenReturn(new PageImpl<>(Collections.singletonList(fakeUser), pr,
                1L));
        //when
        Page<UserDto> result = userService.getAllUsers(0, 1);
        verify(userRepo).findAll(pr);
        //Then
        assert (result.getContent().size() == 1);
        assertNotNull(result);
    }

    @Test
    void getUserByPhoneNumber() {
        //given
        doReturn(Optional.of(fakeUser)).when(userRepo)
            .findByPhoneNumber(phoneNumber);
        //when
        UserDto userReturned =
            userService.getUserByPhoneNumber(phoneNumber);
        //then
        verify(userRepo).findByPhoneNumber(phoneNumber);
        assertEquals(userReturned.getPhoneNumber(), fakeUser.getPhoneNumber());
    }

    @Test
    void shouldThrowNoUserFoundException() {

        //given
        when(userRepo.findByPhoneNumber(eq("unknown")))
            .thenReturn(Optional.empty());

        //when
        NoUserFoundException thrown =
            assertThrows(NoUserFoundException.class, () -> {
                userService.getUserByPhoneNumber("unknown");
            });
        //then
        assertEquals("No user found with phoneNumber unknown.",
            thrown.getMessage());
        verify(userRepo).findByPhoneNumber("unknown");
    }

    private UserRepository createUserRepository() {
        //create Mock class
        return mock(UserRepository.class);
    }

    private User createFakeUser() {
        User fakeUser = User.builder()
            .firstName(faker.name().firstName())
            .middleName(faker.name().lastName())
            .lastName(faker.name().firstName())
            .phoneNumber(phoneNumber)
            .enabled(true)
            .employment(Employment.FULL_TIME)
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
    }

    private AzModelMapper createModelMapper() {
        return null;
    }

}