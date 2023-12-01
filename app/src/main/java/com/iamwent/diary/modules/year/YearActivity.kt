package com.iamwent.diary.modules.year

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.iamwent.diary.R
import com.iamwent.diary.databinding.ActivityYearBinding
import com.iamwent.diary.modules.DiaryViewModel
import com.iamwent.diary.modules.month.MonthActivity
import com.iamwent.diary.widget.OnRecyclerItemClickedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class YearActivity : ComponentActivity() {

    private val binding: ActivityYearBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_year)
    }
    private val adapter by lazy { YearListAdapter() }

    private val diaryViewModel by viewModels<DiaryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_year)
        binding.years.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        binding.years.addOnItemTouchListener(object :
            OnRecyclerItemClickedListener(this@YearActivity) {
            override fun onItemClick(view: View?, position: Int) {
                adapter.currentList.getOrNull(position)?.let { year ->
                    MonthActivity.start(this@YearActivity, year)
                }
            }
        })
        binding.years.adapter = adapter

        lifecycleScope.launch {
            diaryViewModel.getYears().collectLatest {
                adapter.submitList(it)
            }
        }
    }


    companion object {
        fun start(context: Context) {
            val starter = Intent(context, YearActivity::class.java)
            context.startActivity(starter)
        }
    }
}
