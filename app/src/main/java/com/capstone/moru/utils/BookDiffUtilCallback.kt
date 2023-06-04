package com.capstone.moru.utils

import androidx.recyclerview.widget.DiffUtil
import com.capstone.moru.data.db.model.BooksRoutineModel

class BookDiffUtilCallback : DiffUtil.ItemCallback<BooksRoutineModel>() {
    override fun areItemsTheSame(oldItem: BooksRoutineModel, newItem: BooksRoutineModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: BooksRoutineModel,
        newItem: BooksRoutineModel
    ): Boolean {
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