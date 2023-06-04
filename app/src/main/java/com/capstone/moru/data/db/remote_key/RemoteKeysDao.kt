package com.capstone.moru.data.db.remote_key

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKey(remoteKeys: List<ExerciseRemoteKey>)

    @Query("SELECT * FROM exercise_remote_keys WHERE id = :id")
    suspend fun getRemoteKey(id: String):ExerciseRemoteKey?

    @Query("DELETE FROM exercise_remote_keys")
    suspend fun deleteAllRemoteKeys()
}