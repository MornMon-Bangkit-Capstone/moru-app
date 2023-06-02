package com.capstone.moru.data.db.user_routine

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.capstone.moru.data.db.model.RoutineModel

@Database(entities = [RoutineModel::class], version = 1, exportSchema = false)
abstract class UserRoutineDatabase : RoomDatabase() {
    abstract fun userRoutineDao(): UserRoutineDao

    companion object {
        @Volatile
        private var INSTANCE: UserRoutineDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserRoutineDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    UserRoutineDatabase::class.java, "user_routine_database"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}