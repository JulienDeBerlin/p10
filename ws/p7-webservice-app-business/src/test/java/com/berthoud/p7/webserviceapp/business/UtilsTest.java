package com.berthoud.p7.webserviceapp.business;

import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;


public class UtilsTest {

    @Test
    public void convertListStringIntoSetString(){
        List<String> stringList = new ArrayList<>();
        stringList.add("Ich ");
        stringList.add("bin ");
        stringList.add("ein ");
        stringList.add("Berliner. ");

        assertEquals(HashSet.class, Utils.convertListStringIntoSetString(stringList).getClass());
        assertEquals(4, Utils.convertListStringIntoSetString(stringList).size());
        assertTrue(Utils.convertListStringIntoSetString(stringList).contains("Ich "));
        assertTrue(Utils.convertListStringIntoSetString(stringList).contains("bin "));
        assertTrue(Utils.convertListStringIntoSetString(stringList).contains("ein "));
        assertTrue(Utils.convertListStringIntoSetString(stringList).contains("Berliner. "));
    }

    @Test
    public void testConvertLocalDateTimeForXml() throws DatatypeConfigurationException {
        LocalDateTime localDateTime1= LocalDateTime.now();
        LocalDateTime localDateTime2= LocalDateTime.now(Clock.systemUTC());
        Utils.convertLocalDateTimeForXml(localDateTime1);

    }
}
