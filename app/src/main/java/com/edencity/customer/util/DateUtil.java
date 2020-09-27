package com.edencity.customer.util;

/**
 * Created by 紫钢 on 2015/5/25.
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static DateFormat defaultDateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    public static DateFormat tightDateTime=new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
    public static DateFormat tightDate=new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
    public static DateFormat looseDate=new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    public static DateFormat tightTime=new SimpleDateFormat("HHmmss", Locale.CHINA);

    public static String getTightDateTime(Date date){
        return tightDateTime.format(date==null?new Date():date);
    }

    public static String getLooseDateTime(Date date){
        return defaultDateTime.format(date==null?new Date():date);
    }

    public static String getLooseDate(Date date){
        return looseDate.format(date==null?new Date():date);
    }

    public static String getTightDate(Date date){
        return tightDate.format(date==null?new Date():date);
    }

    public static String getTightTime(Date date){
        return tightTime.format(date==null?new Date():date);
    }

    public static String getTimeStamp()
    {
        return String.valueOf(new Date().getTime()/1000);
    }

    public static Date parseLooseDateTime(String date){
        try {
            return defaultDateTime.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseLooseDate(String date){
        try {
            return looseDate.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseTightDateTime(String date){
        try {
            return tightDateTime.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date parseUnformatedDate(String date,String time){
        try {
            String d1=date.replace("/", "");
            d1 = d1.replace("-", "");

            String t1=time.replace(":","");
            return tightDateTime.parse(d1+t1);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date parseTightDate(String date){
        try {
            return tightDate.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}

