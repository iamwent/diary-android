package com.iamwent.diary.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by iamwent on 9/20/16.
 *
 * @author iamwent
 * @since 9/20/16
 */
public class LunarUtilTest {

    @Test
    public void year2Chinese() throws Exception {
        assertEquals("二零一六", LunarUtil.year2Chinese(2016));
        assertEquals("二零零零", LunarUtil.year2Chinese(2000));
    }

    @Test
    public void month2Chinese() throws Exception {
        assertEquals("六", LunarUtil.month2Chinese(5));
        assertEquals("十", LunarUtil.month2Chinese(9));
        assertEquals("十二", LunarUtil.month2Chinese(11));
    }

    @Test
    public void day2Chinese() throws Exception {
        assertEquals("一", LunarUtil.day2Chinese(1));

        assertEquals("十", LunarUtil.day2Chinese(10));

        assertEquals("十一", LunarUtil.day2Chinese(11));

        assertEquals("二十", LunarUtil.day2Chinese(20));

        assertEquals("二十一", LunarUtil.day2Chinese(21));

        assertEquals("三十", LunarUtil.day2Chinese(30));

        assertEquals("三十一", LunarUtil.day2Chinese(31));
    }
}