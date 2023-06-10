package com.capstone.moru.utils

import androidx.recyclerview.widget.DiffUtil
import com.capstone.moru.data.api.response.BookListItem

class BookRoutineDiffUtil : DiffUtil.ItemCallback<com.capstone.moru.data.api.response.BookListItem>() {
    override fun areItemsTheSame(oldItem: com.capstone.moru.data.api.response.BookListItem, newItem: com.capstone.moru.data.api.response.BookListItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: com.capstone.moru.data.api.response.BookListItem, newItem: com.capstone.moru.data.api.response.BookListItem): Boolean {
        return when {
            oldItem.iSBN != newItem.iSBN -> false
            oldItem.bookTitle != newItem.bookTitle -> false
            oldItem.bookAuthor != newItem.bookAuthor -> false
            oldItem.yearOfPublication != newItem.yearOfPublication -> false
            oldItem.publisher != newItem.publisher -> false
            oldItem.imageURLL != newItem.imageURLL -> false
            oldItem.author != newItem.author -> false
            oldItem.summary != newItem.summary -> false
            oldItem.avgRating != newItem.avgRating -> false
            oldItem.countRating != newItem.countRating -> false
            oldItem.genres != newItem.genres -> false
            oldItem.isPublic != newItem.isPublic -> false
            else -> true
        }
    }
}