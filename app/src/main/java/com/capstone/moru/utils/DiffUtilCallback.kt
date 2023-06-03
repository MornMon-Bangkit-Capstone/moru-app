package com.capstone.moru.utils

import androidx.recyclerview.widget.DiffUtil
import com.capstone.moru.data.db.model.RoutineModel

class DiffUtilCallback : DiffUtil.ItemCallback<RoutineModel>() {
    override fun areItemsTheSame(oldItem: RoutineModel, newItem: RoutineModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RoutineModel, newItem: RoutineModel): Boolean {
        return when {
            oldItem.id != newItem.id -> false
            oldItem.imgUrl != newItem.imgUrl -> false
            oldItem.description != newItem.description -> false
            oldItem.title != newItem.title -> false
            oldItem.type != newItem.type -> false
            else -> true
        }
    }
}