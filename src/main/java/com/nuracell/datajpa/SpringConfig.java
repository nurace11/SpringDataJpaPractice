package com.nuracell.datajpa;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class SpringConfig {
    @Bean
    RestTemplate restTemplate() {
        System.out.println("RestTemplate by builder");
        return new RestTemplateBuilder().build();
    }
}
