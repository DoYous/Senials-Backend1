package com.senials.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableJpaRepositories("com.senials.common.repository")
@ComponentScan(basePackages = {"com.senials"})
@EntityScan(basePackages = {"com.senials.common.entity"})
public class SpringConfiguration {

}
