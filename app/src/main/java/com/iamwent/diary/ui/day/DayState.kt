package com.iamwent.diary.ui.day

import com.iamwent.diary.data.bean.Diary
import com.iamwent.diary.model.Month
import com.iamwent.diary.model.Year

data class DayState(
    val year: Year = Year(0, ""),
    val month: Month = Month(0, ""),
    val diaries: List<Diary> = emptyList(),
)
