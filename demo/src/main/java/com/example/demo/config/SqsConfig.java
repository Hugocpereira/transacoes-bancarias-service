package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsCliente;

@Configuration
public class SqsConfig {
    @Bean
    public SqsCliente sqsCliente(){
        return SqsCliente.create();
    }
}
