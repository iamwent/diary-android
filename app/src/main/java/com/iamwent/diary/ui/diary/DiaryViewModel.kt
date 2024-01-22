package com.iamwent.diary.ui.diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamwent.diary.data.DiaryRepository
import com.iamwent.diary.data.bean.Diary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
) : ViewModel() {

    private val _diaryState = MutableStateFlow(DiaryState())
    val diaryState: StateFlow<DiaryState> = _diaryState
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = DiaryState()
        )

    fun queryDiary(id: Long?) {
        if (id == null) {
            return
        }

        viewModelScope.launch {
            _diaryState.value = DiaryState(
                diary = diaryRepository.queryDiary(id)
            )
        }
    }

    fun delete(diary: Diary?) {
        if (diary == null) {
            return
        }

        viewModelScope.launch {
            diaryRepository.delete(diary)
        }
    }

}
