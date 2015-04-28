/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by VamsiKrishna on 7/3/15.
 */
public class DateUtils {

    public final static String MYSQL_DT_FMT = "yyyy-MM-dd";
    public final static String MYSQL_DT_FMT_WITH_TIME = "yyyy-MM-dd HH:mm";
    public final static String DISPLAY_DT_FMT = "dd/MM/yyyy";
    public final static String DISPLAY_DT_FMT_WITH_TIME = "dd/MM/yyyy HH:mm";
    public final static String DISPLAY_DT_FMT_WITH_TIME_SS = "dd-MM-yyyy_HH-mm-ss";

    public static String defaultDateFormat(Date d){
        DateFormat df = new SimpleDateFormat(MYSQL_DT_FMT, Locale.UK);
        return df.format(d);
    }

    public static String defaultDateFormatWithTime(Date d){
        DateFormat df = new SimpleDateFormat(MYSQL_DT_FMT_WITH_TIME, Locale.UK);
        return df.format(d);
    }

    public static String getDisplayDate(Date d){
        DateFormat df = new SimpleDateFormat(DISPLAY_DT_FMT, Locale.UK);
        return df.format(d);
    }

    public static Date getDate(String dtStr, String format) throws ParseException {
        DateFormat df = new SimpleDateFormat(format, Locale.UK);
        return df.parse(dtStr);
    }

    public static String getMysqlDateStr(String date, String currentFormat) throws ParseException {
        Date d = getDate(date, currentFormat);
        return defaultDateFormatWithTime(d);
    }


    public static String convertMysqlDateToDisplayDate(String date) throws ParseException {
        Date d = getDate(date, MYSQL_DT_FMT);
        DateFormat df = new SimpleDateFormat(DISPLAY_DT_FMT, Locale.UK);
        return df.format(d);
    }

    public static String getDateStrWithFormat(String date,String fromFormat, String toFormat) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(toFormat, Locale.UK);
        return dateFormat.format(getDate(date, fromFormat));
    }

    public static String getDateInFormat(Date d, String expectedFormat){
        DateFormat dateFormat = new SimpleDateFormat(expectedFormat, Locale.UK);
        return dateFormat.format(d);
    }
}
