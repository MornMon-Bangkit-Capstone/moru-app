package com.capstone.moru.data.db.user_routine

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capstone.moru.data.db.model.RoutineModel

@Dao
interface UserRoutineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserRoutine(routine: List<RoutineModel>)

    @Query("SELECT * FROM user_routines")
    fun getAllUserRoutines(): PagingSource<Int, RoutineModel>

    @Query("DELETE FROM user_routines")
    suspend fun deleteAllUserRoutines()
}