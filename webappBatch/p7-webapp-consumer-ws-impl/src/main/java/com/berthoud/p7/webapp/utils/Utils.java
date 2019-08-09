package com.berthoud.p7.webapp.utils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;

public class Utils {

    public static LocalDate convertXmlDateToLocal(XMLGregorianCalendar date)  {

        return  date.toGregorianCalendar().toZonedDateTime().toLocalDate();
    }



}
