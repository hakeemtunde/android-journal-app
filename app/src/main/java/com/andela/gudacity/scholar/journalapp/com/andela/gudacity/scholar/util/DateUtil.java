package com.andela.gudacity.scholar.journalapp.com.andela.gudacity.scholar.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static final String DATE_FORMAT = "dd/MM/yyy";

    private static SimpleDateFormat dateFormat =
            new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    public static String getDateFormat(Date date) {
        return date == null ? null : dateFormat.format(date);
    }
}
