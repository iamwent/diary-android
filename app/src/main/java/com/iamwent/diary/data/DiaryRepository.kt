package com.iamwent.diary.data

import android.annotation.SuppressLint
import com.iamwent.diary.data.bean.Diary
import com.iamwent.diary.data.local.DiaryDatabase
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

    @SuppressLint("Range")
    fun queryYears(): List<Int> {
        return diaryDatabase.diaryDao().queryYears()
    }

    @SuppressLint("Range")
    fun queryMonthsByYear(year: Int): List<Int> {
        return diaryDatabase.diaryDao().queryMonthsByYear(year)
    }

    fun queryDiary(id: Long): Diary? {
        return diaryDatabase.diaryDao().queryDiary(id)
    }

    fun queryDaysByYearAndMonth(year: Int, month: Int): List<Diary> {
        return diaryDatabase.diaryDao().queryDaysByYearAndMonth(year, month)
    }

    fun insert(diary: Diary) {
        diaryDatabase.diaryDao().insert(diary)
    }

    fun update(diary: Diary) {
        diaryDatabase.diaryDao().update(diary)
    }

    fun delete(diary: Diary) {
        diaryDatabase.diaryDao().delete(diary)
    }

}
