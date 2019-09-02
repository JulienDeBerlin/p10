package com.berthoud.p7.webserviceapp.tests;

import com.berthoud.p7.webserviceapp.business.LoanManager;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

//@SpringBootConfiguration
//@EnableAutoConfiguration

@SpringBootConfiguration
public class WsBusinessTestConfiguration {

    @Bean
    LoanManager loanManager(){
        return new LoanManager();
    }

}
