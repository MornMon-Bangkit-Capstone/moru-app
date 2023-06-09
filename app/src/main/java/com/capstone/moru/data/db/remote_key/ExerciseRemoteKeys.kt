package com.capstone.moru.data.db.remote_key

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_remote_keys")
data class ExerciseRemoteKey(@PrimaryKey val id: String, val prevKey: Int?, val nextKey: Int?)