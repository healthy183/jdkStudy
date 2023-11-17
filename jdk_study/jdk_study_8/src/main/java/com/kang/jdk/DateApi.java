package com.kang.jdk;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * User:
 * Description:
 * Date: 2023-06-23
 * Time: 13:51
 */
public class DateApi {

    public static void main(String[] args) {
        String patternLocalDate = "yyyy年MM月dd日";
        LocalDate now = LocalDate.now();
        formatDate(patternLocalDate, now);

        String patternLocalDateTime = "yyyy年MM月dd日 HH小时mm分ss秒";
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(patternLocalDateTime);
        String localDateTimeFormat = currentTime.format(dateTimeFormatter);
        System.out.println(localDateTimeFormat);

        LocalDate localDate = currentTime.toLocalDate();
        formatDate(patternLocalDate, localDate);

        int year = currentTime.getYear();
        Month month = currentTime.getMonth();
        int dayOfMonth = currentTime.getDayOfMonth();
        DayOfWeek dayOfWeek = currentTime.getDayOfWeek();
        System.out.println(year +"-" + month +"-" + dayOfMonth +"," + dayOfWeek);

        LocalDateTime date2 = currentTime.withDayOfMonth(10).withYear(2012);
        System.out.println("date2: " + date2);
        // 12 december 2014
        LocalDate date3 = LocalDate.of(2014, Month.DECEMBER, 12);
        System.out.println("date3: " + date3);

        // 22 小时 15 分钟
        LocalTime date4 = LocalTime.of(22, 15);
        System.out.println("date4: " + date4);

        // 解析字符串
        LocalTime date5 = LocalTime.parse("20:15:30");
        System.out.println("date5: " + date5);


    }

    private static void formatDate(String pattern, LocalDate now) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        String format = now.format(dateTimeFormatter);
        System.out.println(format);
    }


}
