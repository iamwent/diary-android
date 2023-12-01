package com.iamwent.diary.modules.year

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iamwent.diary.databinding.ItemYearBinding
import com.iamwent.diary.utils.LunarUtil

/**
 * Created by iamwent on 9/19/16.
 *
 * @author iamwent
 * @since 9/19/16
 */
internal class YearListAdapter : ListAdapter<Int, YearViewHolder>(YearItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemYearBinding.inflate(inflater, parent, false)
        return YearViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YearViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

internal class YearViewHolder(
    private val binding: ItemYearBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(year: Int) {
        binding.year.text = "%s年".format(LunarUtil.year2Chinese(year))
    }
}

object YearItemCallback : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
}
