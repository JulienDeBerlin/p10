package com.berthoud.p7.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApp {

    public static Logger logger = LoggerFactory.getLogger(WebApp.class);


    public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);


    }



}