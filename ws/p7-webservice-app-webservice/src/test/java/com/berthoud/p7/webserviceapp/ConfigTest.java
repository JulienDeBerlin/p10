package com.berthoud.p7.webserviceapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:notificationEmail.properties")
})

public class ConfigTest {

    @Value("${loanLengthInDays}")
    private String loanLengthInDays;

    @Value("${maxExtensions}")
    private String maxExtensions;

    @Value("${extensionLengthInDays}")
    private String extensionLengthInDays;


    @Test
    public void contextLoads() {
    }

    @Test
    public void testImportProperties() {
        String test = loanLengthInDays;
        assertEquals(test, "28");

        LocalDate testDate = LocalDate.now().plusDays(Integer.parseInt(loanLengthInDays));
        testDate = testDate.plusDays(1);

        String test2 = extensionLengthInDays;
        assertEquals(test2, "28");

        String test3 = maxExtensions;
        assertEquals(test3, "1");
    }


}
