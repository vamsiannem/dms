/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.utils;

import junit.framework.TestCase;
import org.junit.*;

import java.text.ParseException;

/**
 * Created by VamsiKrishna on 18/4/15.
 */
public class DateUtilsTest extends TestCase {

    @org.junit.Test
    public void testGetDateStrWithFormat() {
        String inputDt = "2014-10-24 10:25:05";
        String output = null;
        try {
            output = DateUtils.getDateStrWithFormat(inputDt, "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm");
        } catch (ParseException e) {
            Assert.assertNotNull(output);
        }
        Assert.assertNotNull(output);
        System.out.print(output);
        /*DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);
        Date date =null;
        try {
            date = df.parse(inputDt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);*/
    }

    @org.junit.Test
    public void testGetMysqlDateStr() {
        String inputDtFormat = DateUtils.DISPLAY_DT_FMT_WITH_TIME;
        String outputFormat = DateUtils.MYSQL_DT_FMT_WITH_TIME;
        String inputDt = "20/04/2015 13:34";
        String outputDt = null;
        try {
             outputDt = DateUtils.getMysqlDateStr(inputDt, inputDtFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("input Dt: "+ inputDt+"\n output Dt:"+outputDt);
        Assert.assertNotNull(outputDt);
    }
}
