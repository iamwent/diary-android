package com.iamwent.diary.modules.year

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iamwent.diary.R
import com.iamwent.diary.data.DiaryRepository
import com.iamwent.diary.modules.month.MonthActivity
import com.iamwent.diary.widget.OnRecyclerItemClickedListener

class YearActivity : Activity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: YearListAdapter? = null
    var years = emptyList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_year)
        recyclerView = findViewById<View>(R.id.year) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        recyclerView!!.addOnItemTouchListener(object : OnRecyclerItemClickedListener(this@YearActivity) {
            override fun onItemClick(view: View?, position: Int) {
                MonthActivity.start(this@YearActivity, years[position])
            }
        })
        adapter = YearListAdapter(this, years)
        recyclerView!!.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        years = DiaryRepository.getInstance(this)!!.queryYears()
        adapter = YearListAdapter(this, years)
        recyclerView!!.adapter = adapter
    }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, YearActivity::class.java)
            context.startActivity(starter)
        }
    }
}
