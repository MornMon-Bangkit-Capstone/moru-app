package com.capstone.moru.di

import android.content.Context
import com.capstone.moru.BuildConfig
import com.capstone.moru.data.api.retrofit.ApiConfig
import com.capstone.moru.data.repository.UserRepository
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
        val apiService = ApiConfig.getApiService(client)

        return UserRepository.getInstance(apiService)
    }
}