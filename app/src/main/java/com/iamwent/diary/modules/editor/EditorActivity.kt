package com.iamwent.diary.modules.editor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.iamwent.diary.R
import com.iamwent.diary.data.bean.Diary
import com.iamwent.diary.databinding.ActivityEditorBinding
import com.iamwent.diary.modules.DiaryViewModel
import com.iamwent.diary.utils.LunarUtil
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class EditorActivity : ComponentActivity() {

    private val binding: ActivityEditorBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_editor)
    }
    private val diaryViewModel by viewModels<DiaryViewModel>()

    private val diary: Diary by lazy {
        intent.getParcelableExtra(EXTRA_DIARY) ?: newDiary()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun newDiary(): Diary {
        val c = Calendar.getInstance()
        return Diary(
            id = 0L,
            year = c[Calendar.YEAR],
            month = c[Calendar.MONTH],
            title = "%s 日".format(LunarUtil.day2Chinese(c[Calendar.DATE])),
            content = "",
            location = "于 深圳",
            createdAt = System.currentTimeMillis()
        )
    }

    private fun init() {
        binding.title.setText(diary.title)

        binding.content.setText(diary.content)
        binding.content.requestFocus()
        binding.content.setSelection(diary.content?.length ?: 0)

        binding.location.setText(diary.location)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val content = binding.content.editableText?.toString() ?: return
        var title = binding.title.editableText?.toString()
        if (title.isNullOrEmpty()) {
            title = "%s 日".format(LunarUtil.day2Chinese(Calendar.getInstance()[Calendar.DATE]))
        }
        val location = binding.location.editableText?.toString()

        val newDiary = diary.copy(
            content = content,
            title = title,
            location = location,
        )
        diaryViewModel.updateOrInsert(newDiary)
    }

    companion object {
        private const val EXTRA_DIARY = "diary"

        @JvmOverloads
        fun start(context: Context, diary: Diary? = null) {
            val starter = Intent(context, EditorActivity::class.java)
            starter.putExtra(EXTRA_DIARY, diary)
            context.startActivity(starter)
        }
    }
}
