package com.capstone.moru.utils

import androidx.recyclerview.widget.DiffUtil
import com.capstone.moru.data.api.response.ScheduleListItem

class ScheduleDiffUtilCallback: DiffUtil .ItemCallback<ScheduleListItem>(){
    override fun areItemsTheSame(oldItem: ScheduleListItem, newItem: ScheduleListItem): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: ScheduleListItem, newItem: ScheduleListItem): Boolean {
        return when{
            oldItem.id != newItem.id -> false
            oldItem.startTime != newItem.startTime -> false
            oldItem.uid != newItem.uid -> false
            oldItem.name != newItem.name -> false
            oldItem.endTime != newItem.endTime -> false
            oldItem.type != newItem.type -> false
            oldItem.status != newItem.status -> false
            else -> true
        }
    }
}