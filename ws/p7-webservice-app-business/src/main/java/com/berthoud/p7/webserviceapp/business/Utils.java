package com.berthoud.p7.webserviceapp.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Utils {

    public static Logger logger = LoggerFactory.getLogger("ws_utils");


    /**
     * This is a small tool that converts a List of String into a set of strings
     *
     * @param keywords the list to be converted
     * @return
     */
    public static Set<String> convertListStringIntoSetString(List<String> keywords) {
        BusinessLogger.logger.trace("entering method convertListStringIntoSetString");

        return new HashSet<>(keywords);
    }

    /**
     * This method converts a LocalDate into an XMLGregorianCalendar object
     *
     * @param localDate input
     * @return XMLGregorianCalendar as output
     * @throws DatatypeConfigurationException
     */
    public static XMLGregorianCalendar convertLocalDateForXml(LocalDate localDate) throws DatatypeConfigurationException {
        XMLGregorianCalendar out = DatatypeFactory.newInstance().newXMLGregorianCalendar(localDate.toString());
        return out;
    }

    /**
     * This method converts a LocalDateTime into an XMLGregorianCalendar object
     *
     * @param localDateTime input
     * @return XMLGregorianCalendar as output
     * @throws DatatypeConfigurationException
     */
    public static XMLGregorianCalendar convertLocalDateTimeForXml(LocalDateTime localDateTime) throws DatatypeConfigurationException {
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        XMLGregorianCalendar xmlDate = null;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        try {
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return xmlDate;
    }


}
