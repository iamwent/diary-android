package com.iamwent.diary.modules.month

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iamwent.diary.databinding.ItemMonthBinding
import com.iamwent.diary.utils.LunarUtil

/**
 * Created by iamwent on 9/19/16.
 *
 * @author iamwent
 * @since 9/19/16
 */
internal class MonthListAdapter : ListAdapter<Int, MonthViewHolder>(MonthItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMonthBinding.inflate(inflater, parent, false)
        return MonthViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

internal class MonthViewHolder(
    private val binding: ItemMonthBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(month: Int) {
        binding.month.text = "%s月".format(LunarUtil.month2Chinese(month))
    }
}

object MonthItemCallback : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
}
