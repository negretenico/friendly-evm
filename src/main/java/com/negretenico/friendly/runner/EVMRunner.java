package com.negretenico.friendly.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class EVMRunner {
    @Bean
    public CommandLineRunner run(){
        return args ->{

        };
    }
}
