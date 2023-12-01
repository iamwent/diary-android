package com.iamwent.diary.data

import com.iamwent.diary.data.bean.Diary
import com.iamwent.diary.data.local.DiaryDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by iamwent on 9/20/16.
 *
 * @author iamwent
 * @since 9/20/16
 */
@Singleton
class DiaryRepository @Inject constructor(
    private val diaryDatabase: DiaryDatabase
) {

    fun queryYears(): Flow<List<Int>> {
        return diaryDatabase.diaryDao().queryYears()
    }

    fun queryMonthsByYear(year: Int): Flow<List<Int>> {
        return diaryDatabase.diaryDao().queryMonthsByYear(year)
    }

    suspend fun queryDiary(id: Long): Diary? {
        return diaryDatabase.diaryDao().queryDiary(id)
    }

    fun queryDiariesByYearAndMonth(year: Int, month: Int): Flow<List<Diary>> {
        return diaryDatabase.diaryDao().queryDaysByYearAndMonth(year, month)
    }

    suspend fun insert(diary: Diary) {
        diaryDatabase.diaryDao().insert(diary)
    }

    suspend fun update(diary: Diary) {
        diaryDatabase.diaryDao().update(diary)
    }

    suspend fun delete(diary: Diary) {
        diaryDatabase.diaryDao().delete(diary)
    }

}
