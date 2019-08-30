package com.berthoud.p7.webserviceapp.business.com.berthoud.p7.webserviceapp.buiness.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;


@Configuration
public class businessConfig {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
