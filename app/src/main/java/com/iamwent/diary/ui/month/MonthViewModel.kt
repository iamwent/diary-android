package com.iamwent.diary.ui.month

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamwent.diary.data.DiaryRepository
import com.iamwent.diary.model.Month
import com.iamwent.diary.model.Year
import com.iamwent.diary.utils.LunarUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class MonthViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
) : ViewModel() {

    private val _monthState = MutableStateFlow(MonthState())
    val monthState: StateFlow<MonthState> = _monthState
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = MonthState()
        )

    fun queryMonths(year: Int?) {
        viewModelScope.launch {
            val currentYear = year ?: LocalDate.now().year
            val months = diaryRepository.queryMonths(currentYear)
            _monthState.value = MonthState(
                year = Year(currentYear, "${LunarUtil.year2Chinese(currentYear)}年"),
                months = months.map { Month(it, "${LunarUtil.month2Chinese(it)}月") }
            )
        }
    }

}
