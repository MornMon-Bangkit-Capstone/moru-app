package com.capstone.moru.utils

import androidx.recyclerview.widget.DiffUtil
import com.capstone.moru.data.api.response.ListItem

class DiffUtilCallback : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
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