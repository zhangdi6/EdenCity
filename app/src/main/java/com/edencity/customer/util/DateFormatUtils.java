package com.edencity.customer.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateFormatUtils {

    public static final String FORMAT_1 = "MM月dd日 E";
    public static final String FORMAT_2 = "yyyyMMdd";
    public static final String FORMAT_3 = "yyyyMMddHHMMss" ;
    public static final String FORMAT_4 = "yyyy-MM-dd";
    public static final String FORMAT_5 = "yyyy年MM月dd日 HH:MM";
    public static final String FORMAT_6 = "yyyy-MM-dd HH:MM:ss";


    public static String getFormattedDateString(String value, String format){

        Date date = getDateFromString(value);
        if(date != null){
           return new SimpleDateFormat(format, Locale.CHINA).format(date);
        }

        return null;
    }


    public static String longToDate(long lo ,String Format) {

        Date date = new Date(lo);

        SimpleDateFormat sd = new SimpleDateFormat(Format);

        Log.e("time",sd.format(date));
        return sd.format(date);
    }
    public static Date getDateFromString(String value){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_2, Locale.CHINA);
        try {
           return simpleDateFormat.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

}
