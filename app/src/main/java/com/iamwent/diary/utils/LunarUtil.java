package com.iamwent.diary.utils;

import java.util.Calendar;

/**
 * Created by iamwent on 9/19/16.
 *
 * @author iamwent
 * @since 9/19/16
 */
public class LunarUtil {

    private static final String[] numbers = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};

    public static String time2Chinese(long timeInMillis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeInMillis);

        String year = year2Chinese(c.get(Calendar.YEAR));
        String month = month2Chinese(c.get(Calendar.MONTH));
        String day = day2Chinese(c.get(Calendar.DATE));

        return String.format("%s年 %s月 %s日", year, month, day);
    }

    public static String year2Chinese(int year) {
        String chinese = "";

        do {
            int tail = year % 10;
            year /= 10;

            chinese = numbers[tail] + chinese;
        } while (year > 0);

        return chinese;
    }

    public static String month2Chinese(int month) {
        month = month + 1;

        String chinese;
        if (month > 10) {
            chinese = numbers[10] + numbers[month % 10];
        } else if (month == 10) {
            chinese = numbers[10];
        } else {
            chinese = numbers[month];
        }

        return chinese;
    }

    public static String day2Chinese(int day) {
        String chinese;
        if (day % 10 == 0) {
            if (day == 10) {
                chinese = numbers[day];
            } else {
                chinese = numbers[day / 10] + numbers[10];
            }
        } else {
            if (day > 20) {
                chinese = numbers[day / 10] + numbers[10] + numbers[day % 10];
            } else if (day > 10) {
                chinese = numbers[10] + numbers[day % 10];
            } else {
                chinese = numbers[day];
            }
        }

        return chinese;
    }
}
