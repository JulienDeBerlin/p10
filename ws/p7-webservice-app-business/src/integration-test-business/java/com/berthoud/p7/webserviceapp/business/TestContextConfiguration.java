package com.berthoud.p7.webserviceapp.business;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootConfiguration
@ComponentScan(basePackages = "com.berthoud.p7.webserviceapp")
@EnableJpaRepositories(basePackages = "com.berthoud.p7.webserviceapp.consumer")
@EntityScan(basePackages = "com.berthoud.p7.webserviceapp")
@EnableAutoConfiguration
public class TestContextConfiguration {


}
