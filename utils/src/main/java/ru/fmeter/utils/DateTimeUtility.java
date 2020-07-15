package ru.fmeter.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtility {
    private static final String AREAL_DATE_FORMATTER = "dd.MM.yyyy";

    public static String dateToString(Date date) {
        return date != null ? new SimpleDateFormat(AREAL_DATE_FORMATTER).format(date) : null;
    }

    public static Date stringToDate(String date) {
        try {
            return date != null ? new SimpleDateFormat(AREAL_DATE_FORMATTER).parse(date) : null;
        } catch (ParseException e) {
            return null;
        }
    }
}
