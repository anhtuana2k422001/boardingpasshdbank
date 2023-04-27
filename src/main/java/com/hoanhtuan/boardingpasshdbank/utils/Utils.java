package com.hoanhtuan.boardingpasshdbank.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class Utils {
    private Utils() {}

    public static String getResponseTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public static String getRandomUUID(){
        return UUID.randomUUID().toString();
    }
}
