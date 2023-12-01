package com.iamwent.diary.modules.month

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iamwent.diary.R
import com.iamwent.diary.data.DiaryRepository
import com.iamwent.diary.modules.day.DayActivity
import com.iamwent.diary.modules.editor.EditorActivity
import com.iamwent.diary.utils.LunarUtil
import com.iamwent.diary.widget.OnRecyclerItemClickedListener

class MonthActivity : Activity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: MonthListAdapter? = null
    private var months = emptyList<Int>()
    private var year = 0
    private var titleYear: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month)
        year = intent.getIntExtra(EXTRA_YEAR, 0)
        recyclerView = findViewById<View>(R.id.month) as RecyclerView
        titleYear = findViewById<View>(R.id.tv_year) as TextView
        adapter = MonthListAdapter(this, months)
        recyclerView!!.adapter = adapter
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.addOnItemTouchListener(object :
            OnRecyclerItemClickedListener(this@MonthActivity) {
            override fun onItemClick(view: View?, position: Int) {
                DayActivity.start(this@MonthActivity, year, months[position])
            }
        })
        titleYear!!.text = String.format("%s年", LunarUtil.year2Chinese(year))
        findViewById<View>(R.id.btn_compose).setOnClickListener { EditorActivity.start(this@MonthActivity) }
    }

    override fun onResume() {
        super.onResume()
        months = DiaryRepository.getInstance(this)!!.queryMonthsByYear(year)
        adapter = MonthListAdapter(this, months)
        recyclerView!!.adapter = adapter
    }

    companion object {
        private const val EXTRA_YEAR = "year"
        fun start(context: Context, year: Int) {
            val starter = Intent(context, MonthActivity::class.java)
            starter.putExtra(EXTRA_YEAR, year)
            context.startActivity(starter)
        }
    }
}
