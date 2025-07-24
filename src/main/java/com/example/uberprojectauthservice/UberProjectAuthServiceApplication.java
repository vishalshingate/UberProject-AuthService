package com.example.uberprojectauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = {"com.example.uberprojectentity.models"}) // this we have added to tell spring boot to scan entities from there also
public class UberProjectAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UberProjectAuthServiceApplication.class, args);
    }

}
