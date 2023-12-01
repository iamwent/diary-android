package com.iamwent.diary.utils

import com.iamwent.diary.utils.LunarUtil.day2Chinese
import com.iamwent.diary.utils.LunarUtil.month2Chinese
import com.iamwent.diary.utils.LunarUtil.year2Chinese
import org.junit.Assert
import org.junit.Test

/**
 * Created by iamwent on 9/20/16.
 *
 * @author iamwent
 * @since 9/20/16
 */
class LunarUtilTest {
    @Test
    @Throws(Exception::class)
    fun year2Chinese() {
        Assert.assertEquals("二零一六", year2Chinese(2016))
        Assert.assertEquals("二零零零", year2Chinese(2000))
    }

    @Test
    @Throws(Exception::class)
    fun month2Chinese() {
        Assert.assertEquals("六", month2Chinese(5))
        Assert.assertEquals("十", month2Chinese(9))
        Assert.assertEquals("十二", month2Chinese(11))
    }

    @Test
    @Throws(Exception::class)
    fun day2Chinese() {
        Assert.assertEquals("一", day2Chinese(1))
        Assert.assertEquals("十", day2Chinese(10))
        Assert.assertEquals("十一", day2Chinese(11))
        Assert.assertEquals("二十", day2Chinese(20))
        Assert.assertEquals("二十一", day2Chinese(21))
        Assert.assertEquals("三十", day2Chinese(30))
        Assert.assertEquals("三十一", day2Chinese(31))
    }
}