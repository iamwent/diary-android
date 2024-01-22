package com.iamwent.diary.ui.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamwent.diary.data.DiaryRepository
import com.iamwent.diary.data.bean.Diary
import com.iamwent.diary.extension.toDefaultEpochMilli
import com.iamwent.diary.utils.LunarUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class ComposeViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
) : ViewModel() {

    private val _composeState = MutableStateFlow(ComposeState())
    val composeState: StateFlow<ComposeState> = _composeState.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ComposeState(),
    )

    fun load(id: Long?) {
        viewModelScope.launch {
            val diary = id?.let { diaryRepository.queryDiary(id) }
                ?: run {
                    val now = LocalDateTime.now()
                    Diary(
                        year = now.year,
                        month = now.monthValue,
                        title = "%s 日".format(LunarUtil.day2Chinese(now.dayOfMonth)),
                        location = "于 武汉",
                        createdAt = now.toDefaultEpochMilli(),
                    )
                }
            _composeState.value = ComposeState(diary)
        }
    }

    fun compose(
        title: String? = null,
        content: String? = null,
        location: String? = null
    ) {
        val state = _composeState.value
        val diary = state.diary
        _composeState.value = state.copy(
            diary = diary.copy(
                title = title ?: diary.title,
                content = content ?: diary.content,
                location = location ?: diary.location,
            )
        )

    }

    suspend fun save(): Long? {
        val diary = _composeState.value.diary
        if (diary.id != null) {
            diaryRepository.update(diary)
            return diary.id
        } else {
            if (diary.title.isNullOrBlank() && diary.content.isNullOrBlank()) {
                return null
            }
            return diaryRepository.insert(diary)
        }
    }
}
