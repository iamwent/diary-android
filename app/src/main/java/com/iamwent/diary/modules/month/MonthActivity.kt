package com.iamwent.diary.modules.month

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iamwent.diary.R
import com.iamwent.diary.databinding.ActivityMonthBinding
import com.iamwent.diary.modules.DiaryViewModel
import com.iamwent.diary.modules.day.DayActivity
import com.iamwent.diary.modules.editor.EditorActivity
import com.iamwent.diary.utils.LunarUtil
import com.iamwent.diary.widget.OnRecyclerItemClickedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MonthActivity : ComponentActivity() {

    private val binding: ActivityMonthBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_month)
    }
    private val adapter by lazy { MonthListAdapter() }

    private val diaryViewModel by viewModels<DiaryViewModel>()

    private var year = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        year = intent.getIntExtra(EXTRA_YEAR, 0)
        binding.months.adapter = adapter
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        binding.months.layoutManager = layoutManager
        binding.months.addOnItemTouchListener(object :
            OnRecyclerItemClickedListener(this@MonthActivity) {
            override fun onItemClick(view: View?, position: Int) {
                adapter.currentList.getOrNull(position)?.let { month ->
                    DayActivity.start(this@MonthActivity, year, month)
                }
            }
        })
        binding.year.text = String.format("%s年", LunarUtil.year2Chinese(year))
        binding.compose.setOnClickListener { EditorActivity.start(this@MonthActivity) }

        lifecycleScope.launch {
            diaryViewModel.getMonths(year).collectLatest {
                adapter.submitList(it)
            }
        }
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
