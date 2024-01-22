package com.iamwent.diary.ui.year

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamwent.diary.data.DiaryRepository
import com.iamwent.diary.model.Year
import com.iamwent.diary.utils.LunarUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class YearViewModel @Inject constructor(
    diaryRepository: DiaryRepository,
) : ViewModel() {

    val yearState: StateFlow<YearState> = diaryRepository.yearFlow.map { years ->
        YearState(
            years = years.map {
                Year(it, "${LunarUtil.year2Chinese(it)}年")
            }
        )
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = YearState()
    )

}
