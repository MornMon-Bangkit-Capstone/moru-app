package com.capstone.moru.data.db.remote_key

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books_remote_keys")
data class BooksRemoteKeys(@PrimaryKey val id: String, val prevKey: Int?, val nextKey: Int?)