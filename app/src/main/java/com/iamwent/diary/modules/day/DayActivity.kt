package com.iamwent.diary.modules.day

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
import com.iamwent.diary.data.DiaryRepository
import com.iamwent.diary.databinding.ActivityDayBinding
import com.iamwent.diary.modules.DiaryViewModel
import com.iamwent.diary.modules.editor.EditorActivity
import com.iamwent.diary.modules.preview.PreviewActivity
import com.iamwent.diary.utils.LunarUtil
import com.iamwent.diary.widget.OnRecyclerItemClickedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DayActivity : ComponentActivity() {

    private val binding: ActivityDayBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_day)
    }
    private val adapter by lazy { DayListAdapter() }
    private val diaryViewModel by viewModels<DiaryViewModel>()

    private val year by lazy {
        intent.getIntExtra(EXTRA_YEAR, 0)
    }
    private val month by lazy {
        intent.getIntExtra(EXTRA_MONTH, 0)
    }

    @Inject
    lateinit var diaryRepository: DiaryRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.days.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        binding.days.addOnItemTouchListener(object :
            OnRecyclerItemClickedListener(this@DayActivity) {
            override fun onItemClick(view: View?, position: Int) {
                adapter.currentList.getOrNull(position)?.let { diary ->
                    PreviewActivity.start(this@DayActivity, diary)
                }
            }
        })
        binding.days.adapter = adapter
        binding.year.text = "%s年".format(LunarUtil.year2Chinese(year))
        binding.month.text = "%s月".format(LunarUtil.month2Chinese(month))
        binding.compose.setOnClickListener {
            EditorActivity.start(this)
        }

        lifecycleScope.launch {
            diaryViewModel.getDiaries(year, month).collectLatest { diaries ->
                adapter.submitList(diaries)
            }
        }
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
