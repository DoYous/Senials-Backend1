package com.senials.example.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableJpaRepositories("com.senials.repository")
@ComponentScan(basePackages = {"com.senials"})
public class ContextConfiguration {

}
