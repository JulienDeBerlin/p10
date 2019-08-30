package com.berthoud.p7.webserviceapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class WebserviceApp {

    public static Logger logger = LoggerFactory.getLogger(WebserviceApp.class);


    public static void main(String[] args) {
        SpringApplication.run(WebserviceApp.class, args);
    }


}