package com.iamwent.diary.modules.day

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iamwent.diary.data.bean.Diary
import com.iamwent.diary.databinding.ItemDayBinding

/**
 * Created by iamwent on 9/19/16.
 *
 * @author iamwent
 * @since 9/19/16
 */
internal class DayListAdapter : ListAdapter<Diary, DiaryViewHolder>(DiaryItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDayBinding.inflate(inflater, parent, false)
        return DiaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

internal class DiaryViewHolder(
    private val binding: ItemDayBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(diary: Diary) {
        binding.day.text = diary.title
    }
}

object DiaryItemCallback : DiffUtil.ItemCallback<Diary>() {
    override fun areItemsTheSame(oldItem: Diary, newItem: Diary) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Diary, newItem: Diary) = oldItem == newItem
}
