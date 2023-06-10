package com.capstone.moru.di

import android.content.Context
import com.capstone.moru.BuildConfig
import com.capstone.moru.data.api.retrofit.ApiConfig
import com.capstone.moru.data.datastore.SettingPreference
import com.capstone.moru.data.db.user_routine.UserRoutineDatabase
import com.capstone.moru.data.repository.UserRepository
import com.capstone.moru.ui.auth.login.dataStore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object Injection {
    fun provideUserRepository(context: Context): UserRepository{

        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        val apiService = com.capstone.moru.data.api.retrofit.ApiConfig.getApiService(client)

        val pref = SettingPreference.getInstance(context.dataStore)

        val userRoutineDatabase = UserRoutineDatabase.getDatabase(context)

        return UserRepository.getInstance(apiService, pref, userRoutineDatabase)
    }
}