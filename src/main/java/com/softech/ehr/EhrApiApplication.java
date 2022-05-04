package com.softech.ehr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EhrApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EhrApiApplication.class, args);
    }

}
