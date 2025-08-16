package com.negretenico.friendly.runner;

import com.negretenico.friendly.vm.EVM;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class EVMRunner {
    @Profile("local")
    @Bean
    public CommandLineRunner run(EVM evm){
        return args ->{
            evm.run();
        };
    }
}
