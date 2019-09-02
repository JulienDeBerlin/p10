package com.berthoud.p7.webserviceapp.utils;

import com.berthoud.p7.webserviceapp.business.BusinessLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utils {

    public static Logger logger = LoggerFactory.getLogger("ws_utils");


    public static XMLGregorianCalendar convertLocalDateForXml(LocalDate localDate) throws DatatypeConfigurationException {

        XMLGregorianCalendar out = DatatypeFactory.newInstance().newXMLGregorianCalendar(localDate.toString());
        return out;
    }

    public static XMLGregorianCalendar convertLocalDateTimeForXml(LocalDateTime localDateTime) throws DatatypeConfigurationException {

        Date date = Date.from( localDateTime.atZone( ZoneId.systemDefault()).toInstant());
        XMLGregorianCalendar xmlDate = null;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        try{
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return xmlDate;
    }







}
