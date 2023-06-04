package com.capstone.moru.data.db.user_routine

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capstone.moru.data.db.model.BooksRoutineModel
import com.capstone.moru.data.db.model.ExerciseRoutineModel

@Dao
interface UserRoutineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserExerciseRoutine(routine: List<ExerciseRoutineModel>)

    @Query("SELECT * FROM user_exercise_routines")
    fun getAllUserExerciseRoutines(): PagingSource<Int, ExerciseRoutineModel>

    @Query("DELETE FROM user_exercise_routines")
    suspend fun deleteAllUserExerciseRoutines()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserBooksRoutine(routine: List<BooksRoutineModel>)

    @Query("SELECT * FROM user_books_routines")
    fun getAllUserBooksRoutines(): PagingSource<Int, BooksRoutineModel>

    @Query("DELETE FROM user_books_routines")
    suspend fun deleteAllUserBooksRoutines()
}