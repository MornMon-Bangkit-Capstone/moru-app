package com.capstone.moru.data.db.remote_key

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllExerciseKey(remoteKeys: List<ExerciseRemoteKey>)

    @Query("SELECT * FROM exercise_remote_keys WHERE id = :id")
    suspend fun getExerciseRemoteKey(id: String):ExerciseRemoteKey?

    @Query("DELETE FROM exercise_remote_keys")
    suspend fun deleteAllExerciseRemoteKeys()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBooksKey(remoteKeys: List<BooksRemoteKeys>)

    @Query("SELECT * FROM books_remote_keys WHERE id = :id")
    suspend fun getBooksRemoteKey(id: String):BooksRemoteKeys?

    @Query("DELETE FROM books_remote_keys")
    suspend fun deleteAllBooksRemoteKeys()
}