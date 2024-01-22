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

    val yearFlow: Flow<List<Int>>
        get() = diaryDatabase.diaryDao().queryYears()

    suspend fun queryMonths(year: Int): List<Int> {
        return diaryDatabase.diaryDao().queryMonth(year)
    }

    suspend fun queryDiary(id: Long): Diary? {
        return diaryDatabase.diaryDao().queryDiary(id)
    }

    suspend fun queryDiaries(year: Int, month: Int): List<Diary> {
        return diaryDatabase.diaryDao().queryDiaries(year, month)
    }

    suspend fun insert(diary: Diary): Long {
        return diaryDatabase.diaryDao().insert(diary)
    }

    suspend fun insertAll(diaries: List<Diary>) {
        return diaryDatabase.diaryDao().insertAll(diaries)
    }

    suspend fun update(diary: Diary) {
        diaryDatabase.diaryDao().update(diary)
    }

    suspend fun delete(diary: Diary) {
        diaryDatabase.diaryDao().delete(diary)
    }

}
