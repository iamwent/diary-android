package com.iamwent.diary.ui.month

import com.iamwent.diary.model.Month
import com.iamwent.diary.model.Year

data class MonthState(
    val year: Year = Year(0, ""),
    val months: List<Month> = emptyList(),
)
