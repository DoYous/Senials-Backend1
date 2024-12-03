package com.senials.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"com.senials.entity"})
@EnableJpaRepositories(basePackages = {
        "com.senials.partyboards.repository",
        "com.senials.user.repository",
        "com.senials.favorites.repository",
        "com.senials.hobby.repository",
        "com.senials.meet.repository",
        "com.senials.likes.repository",
        "com.senials.partyMember.repository"})
@ComponentScan(basePackages = {"com.senials"})
public class SpringConfig {

}
