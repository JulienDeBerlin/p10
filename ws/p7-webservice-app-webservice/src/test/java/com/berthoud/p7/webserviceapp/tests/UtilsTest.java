package com.berthoud.p7.webserviceapp.tests;

import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import com.berthoud.p7.webserviceapp.utils.*;

import javax.xml.datatype.DatatypeConfigurationException;

public class UtilsTest {



    @Test
    public void testConvertLocalDateTimeForXml() throws DatatypeConfigurationException {

        LocalDateTime localDateTime1= LocalDateTime.now();
        LocalDateTime localDateTime2= LocalDateTime.now(Clock.systemUTC());

        Utils.convertLocalDateTimeForXml(localDateTime1);

    }
}
