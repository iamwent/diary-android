package com.iamwent.diary.modules.year

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
internal class YearListAdapter(private val ctx: Context, private val years: List<Int>?) : RecyclerView.Adapter<YearListAdapter.ViewHolder>() {
    @LayoutRes
    private fun provideItemLayout(): Int {
        return R.layout.item_year
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctx).inflate(provideItemLayout(), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(years!![position])
    }

    override fun getItemCount(): Int {
        return years?.size ?: 0
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvYear: TextView

        init {
            tvYear = itemView.findViewById<View>(R.id.tv_year) as TextView
        }

        fun bind(year: Int) {
            tvYear.text = String.format("%s年", LunarUtil.year2Chinese(year))
        }
    }
}
