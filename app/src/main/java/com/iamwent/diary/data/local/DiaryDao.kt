package com.iamwent.diary.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.iamwent.diary.data.bean.Diary
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Query("select distinct year from diaries order by year desc")
    fun queryYears(): Flow<List<Int>>

    @Query("select distinct month from diaries where year = :year order by month asc")
    fun queryMonthsByYear(year: Int): Flow<List<Int>>

    @Query("select distinct month from diaries where year = :year order by month desc")
    suspend fun queryMonth(year: Int): List<Int>

    @Query("select * from diaries where id = :id")
    suspend fun queryDiary(id: Long): Diary?

    @Query("select * from diaries where year = :year and month = :month order by created_at asc")
    fun queryDaysByYearAndMonth(year: Int, month: Int): Flow<List<Diary>>

    @Query("select * from diaries where year = :year and month = :month order by created_at desc")
    suspend fun queryDiaries(year: Int, month: Int): List<Diary>

    @Query("select * from diaries order by created_at desc")
    suspend fun queryAllDiaries(): List<Diary>

    @Insert
    suspend fun insert(diary: Diary): Long

    @Insert
    suspend fun insertAll(diaries: List<Diary>)


    @Update
    suspend fun update(diary: Diary)

    @Delete
    suspend fun delete(diary: Diary)
}
