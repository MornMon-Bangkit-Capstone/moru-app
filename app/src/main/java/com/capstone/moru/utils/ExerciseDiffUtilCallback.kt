package com.capstone.moru.utils

import androidx.recyclerview.widget.DiffUtil
import com.capstone.moru.data.db.model.ExerciseRoutineModel

class ExerciseDiffUtilCallback : DiffUtil.ItemCallback<ExerciseRoutineModel>() {
    override fun areItemsTheSame(oldItem: ExerciseRoutineModel, newItem: ExerciseRoutineModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ExerciseRoutineModel, newItem: ExerciseRoutineModel): Boolean {
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