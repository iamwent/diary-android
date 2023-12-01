package com.iamwent.diary.modules.day

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.iamwent.diary.R
import com.iamwent.diary.data.bean.Diary

/**
 * Created by iamwent on 9/19/16.
 *
 * @author iamwent
 * @since 9/19/16
 */
internal class DayListAdapter(private val ctx: Context, private val diaries: List<Diary>?) : RecyclerView.Adapter<DayListAdapter.ViewHolder>() {
    @LayoutRes
    private fun provideItemLayout(): Int {
        return R.layout.item_day
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctx).inflate(provideItemLayout(), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(diaries!![position])
    }

    override fun getItemCount(): Int {
        return diaries?.size ?: 0
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDay: TextView

        init {
            tvDay = itemView.findViewById<View>(R.id.tv_day) as TextView
        }

        fun bind(diary: Diary) {
            tvDay.text = diary.title
        }
    }
}
