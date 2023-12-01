package com.iamwent.diary.modules.month

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.iamwent.diary.R
import com.iamwent.diary.utils.LunarUtil

/**
 * Created by iamwent on 9/19/16.
 *
 * @author iamwent
 * @since 9/19/16
 */
internal class MonthListAdapter(private val ctx: Context, private val months: List<Int>?) : RecyclerView.Adapter<MonthListAdapter.ViewHolder>() {
    @LayoutRes
    private fun provideItemLayout(): Int {
        return R.layout.item_month
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctx).inflate(provideItemLayout(), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(months!![position])
    }

    override fun getItemCount(): Int {
        return months?.size ?: 0
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvYear: TextView

        init {
            tvYear = itemView.findViewById<View>(R.id.tv_month) as TextView
        }

        fun bind(month: Int) {
            tvYear.text = String.format("%s月", LunarUtil.month2Chinese(month))
        }
    }
}
