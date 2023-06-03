package com.capstone.moru.data.db.user_routine

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.capstone.moru.data.db.model.RoutineModel
import com.capstone.moru.data.db.remote_key.RemoteKeys
import com.capstone.moru.data.db.remote_key.RemoteKeysDao

@Database(entities = [RoutineModel::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class UserRoutineDatabase : RoomDatabase() {
    abstract fun userRoutineDao(): UserRoutineDao

    abstract fun remoteKeysDao(): RemoteKeysDao

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