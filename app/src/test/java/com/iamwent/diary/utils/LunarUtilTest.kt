package com.iamwent.diary.utils

import com.iamwent.diary.utils.LunarUtil.day2Chinese
import com.iamwent.diary.utils.LunarUtil.month2Chinese
import com.iamwent.diary.utils.LunarUtil.year2Chinese
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by iamwent on 9/20/16.
 *
 * @author iamwent
 * @since 9/20/16
 */
class LunarUtilTest {

    @Test
    fun year2Chinese() {
        assertEquals("二零一六", year2Chinese(2016))
        assertEquals("二零零零", year2Chinese(2000))
    }

    @Test
    fun month2Chinese() {
        assertEquals("六", month2Chinese(6))
        assertEquals("十", month2Chinese(10))
        assertEquals("十二", month2Chinese(12))
    }

    @Test
    fun day2Chinese() {
        assertEquals("一", day2Chinese(1))
        assertEquals("十", day2Chinese(10))
        assertEquals("十一", day2Chinese(11))
        assertEquals("廿", day2Chinese(20))
        assertEquals("廿一", day2Chinese(21))
        assertEquals("卅", day2Chinese(30))
        assertEquals("卅一", day2Chinese(31))
    }
}