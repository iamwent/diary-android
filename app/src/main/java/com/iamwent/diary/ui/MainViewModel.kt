package com.iamwent.diary.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamwent.diary.data.DiaryRepository
import com.iamwent.diary.data.bean.Diary
import com.iamwent.diary.extension.toDefaultEpochMilli
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
) : ViewModel() {

    fun check() {
        viewModelScope.launch {
            diaryRepository.yearFlow.collectLatest {
                if (it.isEmpty()) {
                    diaryRepository.insertAll(diaries())
                }
            }
        }
    }

    private fun diaries(): List<Diary> {
        val now = LocalDateTime.now()
        val year = now.year
        val month = now.monthValue
        val createdAt = now.toDefaultEpochMilli()
        return listOf(
            "关雎" to "关关雎鸠\n在河之洲\n窈窕淑女\n君子好逑",
            "女曰鸡鸣" to "宜言饮酒\n与子偕老\n琴瑟在御\n莫不静好",
            "西洲曲" to "海水梦悠悠\n君愁我亦愁\n南风知我意\n吹梦到西洲",
        ).map { (title, content) ->
            Diary(
                year = year,
                month = month,
                title = title,
                content = content,
                location = "于 武汉",
                createdAt = createdAt
            )
        }
    }
}
