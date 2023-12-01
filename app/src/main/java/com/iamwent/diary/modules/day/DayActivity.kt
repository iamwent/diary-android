package com.iamwent.diary.modules.day

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iamwent.diary.R
import com.iamwent.diary.data.DiaryRepository
import com.iamwent.diary.data.bean.Diary
import com.iamwent.diary.modules.editor.EditorActivity
import com.iamwent.diary.modules.preview.PreviewActivity
import com.iamwent.diary.utils.LunarUtil
import com.iamwent.diary.widget.OnRecyclerItemClickedListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DayActivity : ComponentActivity() {
    private var recyclerView: RecyclerView? = null
    private var diaries = emptyList<Diary>()
    private var adapter: DayListAdapter? = null
    private var year = 0
    private var month = 0

    @Inject
    lateinit var diaryRepository: DiaryRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)
        year = intent.getIntExtra(EXTRA_YEAR, 0)
        month = intent.getIntExtra(EXTRA_MONTH, 0)
        val titleYear = findViewById<View>(R.id.tv_year) as TextView
        val titleMonth = findViewById<View>(R.id.tv_month) as TextView
        recyclerView = findViewById<View>(R.id.day) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        recyclerView!!.addOnItemTouchListener(object : OnRecyclerItemClickedListener(this@DayActivity) {
            override fun onItemClick(view: View?, position: Int) {
                PreviewActivity.start(this@DayActivity, diaries[position])
            }
        })
        adapter = DayListAdapter(this, diaries)
        recyclerView!!.adapter = adapter
        titleYear.text = String.format("%s年", LunarUtil.year2Chinese(year))
        titleMonth.text = String.format("%s月", LunarUtil.month2Chinese(month))
        findViewById<View>(R.id.btn_compose).setOnClickListener {
            EditorActivity.start(this)
        }
    }

    override fun onResume() {
        super.onResume()
        diaries = diaryRepository.queryDaysByYearAndMonth(year, month)
        adapter = DayListAdapter(this, diaries)
        recyclerView!!.adapter = adapter
    }

    companion object {
        private const val EXTRA_YEAR = "year"
        private const val EXTRA_MONTH = "month"
        fun start(context: Context, year: Int, month: Int) {
            val starter = Intent(context, DayActivity::class.java)
            starter.putExtra(EXTRA_YEAR, year)
            starter.putExtra(EXTRA_MONTH, month)
            context.startActivity(starter)
        }
    }
}
