package com.kang.proxy.jvm.command.zzoth.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * User:
 * Description:
 * Date: 2023-11-11
 * Time: 18:58
 */
public class DateUtils {

    public static SimpleDateFormat yyyyMMddHHmm  = new SimpleDateFormat("yyyyMMddHHmm");

    public static Calendar roundDownMinute(long time) {
        Calendar endCalendar=Calendar.getInstance();
        endCalendar.setTimeInMillis(time);
        return endCalendar;
    }
}
