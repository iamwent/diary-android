package com.iamwent.diary.ui.day

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
class DayViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
) : ViewModel() {

    private val _dayState = MutableStateFlow(DayState())
    val dayState: StateFlow<DayState> = _dayState
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = DayState()
        )

    fun queryDiaries(year: Int?, month: Int?) {
        viewModelScope.launch {
            val now = LocalDate.now()
            val currentYear = year ?: now.year
            val currentMonth = month ?: now.monthValue
            val diaries = diaryRepository.queryDiaries(currentYear, currentMonth)
            _dayState.value = DayState(
                year = Year(currentYear, "${LunarUtil.year2Chinese(currentYear)}年"),
                month = Month(currentYear, "${LunarUtil.month2Chinese(currentMonth)}月"),
                diaries = diaries,
            )
        }
    }

}
