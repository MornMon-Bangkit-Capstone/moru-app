package com.capstone.moru.utils

import androidx.recyclerview.widget.DiffUtil
import com.capstone.moru.data.api.response.ExerciseListItem

class ExerciseRoutineDiffUtil: DiffUtil.ItemCallback<ExerciseListItem>() {
    override fun areItemsTheSame(oldItem: ExerciseListItem, newItem: ExerciseListItem): Boolean {
        return oldItem == newItem

    }

    override fun areContentsTheSame(oldItem: ExerciseListItem, newItem: ExerciseListItem): Boolean {
        return when{
            oldItem.id != newItem.id -> false
            oldItem.sports != newItem.sports -> false
            oldItem.visual != newItem.visual -> false
            oldItem.durationMin != newItem.durationMin -> false
            oldItem.numberOfPeople != newItem.numberOfPeople -> false
            oldItem.equipment != newItem.equipment -> false
            oldItem.muscle != newItem.muscle -> false
            oldItem.category != newItem.category -> false
            oldItem.isPublic != newItem.isPublic -> false
            else -> true
        }
    }
}