package com.capstone.moru.data.db.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_books_routines")
@Parcelize
class BooksRoutineModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "img_url")
    val imgUrl: String? = null,

    @ColumnInfo(name = "type")
    val type: String? = null,

    @ColumnInfo(name = "description")
    val description: String? = null,

    ): Parcelable