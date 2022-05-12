package com.softech.ehr.configuration;

import com.softech.ehr.dto.EhrModelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EhrConfig {

    @Bean
    public EhrModelMapper modelMapper() {
        return new EhrModelMapper();

    }
}
