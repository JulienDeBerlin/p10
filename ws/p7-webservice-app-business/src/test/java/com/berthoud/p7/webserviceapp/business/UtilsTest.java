package com.berthoud.p7.webserviceapp.business;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.Clock;
import java.time.LocalDate;
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
        assertTrue(Utils.convertLocalDateTimeForXml(localDateTime1) instanceof XMLGregorianCalendar);
    }

    @Test
    public void testConvertLocalDateForXml() throws DatatypeConfigurationException {
        LocalDate localDate= LocalDate.now();
        assertTrue(Utils.convertLocalDateForXml(localDate) instanceof XMLGregorianCalendar);
    }
}
