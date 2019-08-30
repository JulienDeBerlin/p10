package com.berthoud.p7.webserviceapp.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.Clock;
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
