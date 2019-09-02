package com.berthoud.p7.webapp.utils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Utils {

    public static LocalDate convertXmlDateToLocal(XMLGregorianCalendar date)  {

        return  date.toGregorianCalendar().toZonedDateTime().toLocalDate();
    }

    public static LocalDateTime convertXmlDateToLocalDateTime(XMLGregorianCalendar date)  {

        return  date.toGregorianCalendar().toZonedDateTime().toLocalDateTime();
    }



}
