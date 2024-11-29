package com.senials.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"com.senials.example.entity"})
@EnableJpaRepositories(basePackages = {"com.senials.example.repository"})
@ComponentScan(basePackages = {"com.senials"})
public class SpringConfig {

}
