package com.softech.ehr.configuration;

import com.github.javafaker.Faker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzConfig {

    @Bean
    public Faker faker() {
        return new Faker();

    }
}
