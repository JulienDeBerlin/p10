package com.berthoud.p7.webapp.business.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static Logger loggerWebappBusiness = LoggerFactory.getLogger(Utils.class);

    /**
     * Formats a LocalDate in a string formatted as follows: dd/MM/yy
     * @param localDate
     * @return
     */
    public static String localDateFormater (LocalDate localDate){
        return localDate.format(DateTimeFormatter.ofPattern("dd/MM/yy"));

    }

}
