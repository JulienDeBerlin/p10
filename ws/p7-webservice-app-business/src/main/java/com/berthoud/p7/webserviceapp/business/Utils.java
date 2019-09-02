package com.berthoud.p7.webserviceapp.business;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {

    /**
     * This is a small tool to convert a List of String into a set of strings
     *
     * @param keywords the list to be converted
     * @return
     */
    public static Set<String> convertListStringIntoSetString(List<String> keywords) {
        BusinessLogger.logger.trace("entering method convertListStringIntoSetString");

        return new HashSet<>(keywords);
    }
}
