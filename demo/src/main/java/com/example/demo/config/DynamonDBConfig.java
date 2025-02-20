package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.dynamondb.DynamonDbCliente;


@Configuration
public class DynamonDBConfig {
    @Bean
    public DynamonDbCliente dynamonDbCliente(){
        return DynaminDbCliente.create();
    }
}
