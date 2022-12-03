package com.spring.csvupload.api.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    public static final String DATE_FORMAT_MONTH_DAY_YEAR_WITH_DASHES = "MM-dd-yyyy";

    public static Date convertDate(String dateString, String format) {
        if (dateString.equals("")) {
            return null;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            return null;
        }

    }
    private DateConverter(){
        // Do Nothing
    }
}
