package com.softech.ehr;

import com.github.javafaker.Faker;
import com.softech.ehr.dto.AzModelMapper;

import org.mapstruct.factory.Mappers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EhrApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EhrApiApplication.class, args);
    }



}
