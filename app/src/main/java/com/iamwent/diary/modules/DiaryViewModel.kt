package com.iamwent.diary.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamwent.diary.data.DiaryRepository
import com.iamwent.diary.data.bean.Diary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    fun getYears(): Flow<List<Int>> {
        return diaryRepository.queryYears()
    }

    fun getMonths(year: Int): Flow<List<Int>> {
        return diaryRepository.queryMonthsByYear(year)
    }

    fun getDiaries(year: Int, month: Int): Flow<List<Diary>> {
        return diaryRepository.queryDiariesByYearAndMonth(year, month)
    }

    fun delete(diary: Diary) {
        viewModelScope.launch {
            diaryRepository.delete(diary)
        }
    }

    fun updateOrInsert(diary: Diary) {
        viewModelScope.launch {
            if (diary.id != 0L) {
                diaryRepository.update(diary)
            } else {
                diaryRepository.insert(diary)
            }
        }
    }

}
