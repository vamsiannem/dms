/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by VamsiKrishna on 7/3/15.
 */
public class DateUtils {

    public static String defaultDateFormat(Date d){
        String dateFormat = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(dateFormat);
        return df.format(d);
    }
}
