package com.iamwent.diary.utils

import java.util.Calendar

/**
 * Created by iamwent on 9/19/16.
 *
 * @author iamwent
 * @since 9/19/16
 */
object LunarUtil {
    private val numbers = arrayOf("零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十")
    fun time2Chinese(timeInMillis: Long): String {
        val c = Calendar.getInstance()
        c.timeInMillis = timeInMillis
        val year = year2Chinese(c[Calendar.YEAR])
        val month = month2Chinese(c[Calendar.MONTH])
        val day = day2Chinese(c[Calendar.DATE])
        return String.format("%s年 %s月 %s日", year, month, day)
    }

    @JvmStatic
    fun year2Chinese(year: Int): String {
        var year = year
        var chinese = ""
        do {
            val tail = year % 10
            year /= 10
            chinese = numbers[tail] + chinese
        } while (year > 0)
        return chinese
    }

    @JvmStatic
    fun month2Chinese(month: Int): String {
        var month = month
        month = month + 1
        val chinese: String
        chinese = if (month > 10) {
            numbers[10] + numbers[month % 10]
        } else if (month == 10) {
            numbers[10]
        } else {
            numbers[month]
        }
        return chinese
    }

    @JvmStatic
    fun day2Chinese(day: Int): String {
        val chinese: String
        chinese = if (day % 10 == 0) {
            if (day == 10) {
                numbers[day]
            } else {
                numbers[day / 10] + numbers[10]
            }
        } else {
            if (day > 20) {
                numbers[day / 10] + numbers[10] + numbers[day % 10]
            } else if (day > 10) {
                numbers[10] + numbers[day % 10]
            } else {
                numbers[day]
            }
        }
        return chinese
    }
}
