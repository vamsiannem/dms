/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by VamsiKrishna on 7/3/15.
 */
public class DateUtils {

    public final static String MYSQL_DT_FMT = "yyyy-MM-dd";
    public final static String DISPLAY_DT_FMT = "dd/MM/yyyy";

    public static String defaultDateFormat(Date d){
        DateFormat df = new SimpleDateFormat(MYSQL_DT_FMT);
        return df.format(d);
    }

    public static String getDisplayDate(Date d){
        DateFormat df = new SimpleDateFormat(DISPLAY_DT_FMT);
        return df.format(d);
    }


    public static Date getDate(String dtStr, String format) throws ParseException {
        DateFormat df = new SimpleDateFormat(format);
        return df.parse(dtStr);
    }

    public static String getMysqlDateStr(String date, String currentFormat) throws ParseException {
        Date d = getDate(date, currentFormat);
        return defaultDateFormat(d);
    }

    public static String convertMysqlDateToDisplayDate(String date) throws ParseException {
        Date d = getDate(date, MYSQL_DT_FMT);
        DateFormat df = new SimpleDateFormat(DISPLAY_DT_FMT);
        return df.format(d);
    }
}
