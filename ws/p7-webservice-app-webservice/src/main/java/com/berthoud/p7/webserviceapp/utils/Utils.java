package com.berthoud.p7.webserviceapp.utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Utils {

    public static XMLGregorianCalendar convertLocalDateForXml(LocalDate date) throws DatatypeConfigurationException {

        XMLGregorianCalendar out = DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toString());
        return out;
    }

    public static XMLGregorianCalendar convertLocalDateTimeForXml(LocalDateTime date) throws DatatypeConfigurationException {

        XMLGregorianCalendar out = DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toString());
        return out;
    }
}
