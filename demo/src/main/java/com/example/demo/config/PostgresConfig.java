package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
public class PostgresConfig {
    @EnableJpaRepositories(basePackageClasses = "com.example.demo.repository")
}
