package com.hroniko.jload.utils.converters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static String convert(Date date){
        return dateFormat.format(date);
    }

    public static String sysdate(){
        return dateFormat.format(new Date());
    }
}
