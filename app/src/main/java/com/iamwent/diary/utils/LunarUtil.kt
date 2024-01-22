package com.iamwent.diary.utils

import java.time.LocalDateTime

/**
 * Created by iamwent on 9/19/16.
 *
 * @author iamwent
 * @since 9/19/16
 */
object LunarUtil {
    private val numbers = arrayOf(
        "零", "一", "二", "三", "四", "五", "六", "七", "八", "九",
        "十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九",
        "廿", "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九",
        "卅", "卅一",
    )

    fun time2Chinese(dateTime: LocalDateTime): String {
        val year = year2Chinese(dateTime.year)
        val month = month2Chinese(dateTime.monthValue)
        val day = day2Chinese(dateTime.dayOfMonth)
        return "${year}年 ${month}月 ${day}日"
    }

    fun year2Chinese(year: Int): String {
        check(year in 1900..9999) {
            "illegal year range [1900-9999]: $year"
        }

        val builder = StringBuilder()
        var tail = year
        do {
            builder.append(numbers[tail % 10])
            tail /= 10
        } while (tail > 0)
        return builder.reverse().toString()
    }

    fun month2Chinese(month: Int): String {
        check(month in (1..12)) {
            "illegal month range [1-12]: $month"
        }
        return numbers[month]
    }

    fun day2Chinese(day: Int): String {
        check(day in 1..31) {
            "illegal day range [1-31]: $day"
        }
        return numbers[day]
    }
}
