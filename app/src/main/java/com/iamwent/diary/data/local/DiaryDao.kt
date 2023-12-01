package com.iamwent.diary.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.iamwent.diary.data.bean.Diary

@Dao
interface DiaryDao {
    @Query("select distinct year from diaries order by year asc")
    fun queryYears(): List<Int>

    @Query("select distinct month from diaries where year = :year order by month asc")
    fun queryMonthsByYear(year: Int): List<Int>

    @Query("select * from diaries where id = :id")
    fun queryDiary(id: Long): Diary?

    @Query("select * from diaries where year = :year and month = :month order by created_at asc")
    fun queryDaysByYearAndMonth(year: Int, month: Int): List<Diary>

    @Insert
    fun insert(diary: Diary)

    @Update
    fun update(diary: Diary)

    @Delete
    fun delete(diary: Diary)
}
