package com.senials.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
<<<<<<<< HEAD:src/main/java/com/senials/config/SpringConfiguration.java
@EnableJpaRepositories("com.senials.hobbyboard.repository")
@ComponentScan(basePackages = {"com.senials"})
@EntityScan(basePackages = {"com.senials.hobbyboard.entity"})
public class SpringConfiguration {
========
@EntityScan(basePackages = {"com.senials.entity"})
@EnableJpaRepositories(basePackages = {"com.senials.partyboards.repository"})
@ComponentScan(basePackages = {"com.senials"})
public class SpringConfig {
>>>>>>>> origin/example:src/main/java/com/senials/config/SpringConfig.java

}
