package com.softech.ehr.service.impl;

import com.github.javafaker.Faker;
import com.softech.ehr.domain.entity.Address;
import com.softech.ehr.domain.entity.Salary;
import com.softech.ehr.domain.entity.User;
import com.softech.ehr.dto.AzModelMapper;
import com.softech.ehr.dto.response.BasicUserDTO;
import com.softech.ehr.enums.Employment;
import com.softech.ehr.enums.SalaryType;
import com.softech.ehr.enums.Sex;
import com.softech.ehr.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepo;
    @Mock
    AzModelMapper modelMapper;
    Faker faker;
    User fakeUser;
    String phoneNumber = "+251924139590";

    @BeforeEach
    void setUp() {
        faker = new Faker();

        this.fakeUser = User.builder()
            .firstName(faker.name().firstName())
            .middleName(faker.name().lastName())
            .lastName(faker.name().firstName())
            .phoneNumber("+251924139590")
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
        Pageable pr = PageRequest.of(0, 1);
        lenient().when(userRepo.findAll(pr)).thenReturn(
            new PageImpl(Arrays.asList(fakeUser), pr,
                1L));

        lenient().when(userRepo.findByPhoneNumber(phoneNumber))
            .thenReturn(Optional.of(fakeUser));
    }

    @Test
    void shouldFetchPagedAllUsers() {
        //when
        Page<BasicUserDTO> result = userService.getAllUsers(0, 1);
        //Then
        assert (result.getContent().size() == 1);
    }

    @Test
    void getUserByPhoneNumber() {
        //when
        BasicUserDTO userReturned =
            userService.getUserByPhoneNumber(phoneNumber);
        //then
        assertEquals(userReturned.getPhoneNumber(), phoneNumber);
    }
}