package com.iamwent.diary.modules.editor

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.iamwent.diary.R
import com.iamwent.diary.data.DiaryRepository
import com.iamwent.diary.data.bean.Diary
import com.iamwent.diary.utils.LunarUtil
import java.util.Calendar

class EditorActivity : Activity() {
    private var title: EditText? = null
    private var content: EditText? = null
    private var location: EditText? = null
    private var diary: Diary? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        val id = intent.getLongExtra(EXTRA_DIARY, 0L)
        diary = DiaryRepository.getInstance(this)!!.queryDiary(id)
        if (diary == null) {
            diary = newDiary()
        }
        init()
    }

    private fun newDiary(): Diary {
        val c = Calendar.getInstance()
        return Diary(
            id = 0L,
            year = c[Calendar.YEAR],
            month = c[Calendar.MONTH],
            title = String.format("%s 日", LunarUtil.day2Chinese(c[Calendar.DATE])),
            content = "",
            location = "于 深圳",
            createdAt = System.currentTimeMillis()
        )
    }

    private fun init() {
        title = findViewById<View>(R.id.et_title) as EditText
        content = findViewById<View>(R.id.et_content) as EditText
        location = findViewById<View>(R.id.et_location) as EditText
        title!!.setText(diary!!.title)
        content!!.setText(diary!!.content)
        location!!.setText(diary!!.location)
        content!!.isSingleLine = false
        content!!.requestFocus()
        content!!.setSelection(diary!!.content!!.length)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val content = content!!.editableText.toString()
        if (content.length == 0) {
            return
        }
        var title = title!!.editableText.toString()
        if (title.length == 0) {
            title = String.format("%s 日", LunarUtil.day2Chinese(Calendar.getInstance()[Calendar.DATE]))
        }
        val location = location!!.editableText.toString()

        val newDiary = diary?.copy(
            content = content,
            title = title,
            location = location,
        )
        if (newDiary!!.id != 0L) {
            DiaryRepository.getInstance(this)!!.update(newDiary)
        } else {
            DiaryRepository.getInstance(this)!!.insert(newDiary)
        }
    }

    companion object {
        private const val EXTRA_DIARY = "diary"

        @JvmOverloads
        fun start(context: Context, id: Long = 0L) {
            val starter = Intent(context, EditorActivity::class.java)
            starter.putExtra(EXTRA_DIARY, id)
            context.startActivity(starter)
        }
    }
}
