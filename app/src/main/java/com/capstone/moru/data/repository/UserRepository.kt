package com.capstone.moru.data.repository

import com.capstone.moru.data.api.response.LoginResponse
import com.capstone.moru.data.api.response.RegisterResponse
import com.capstone.moru.data.api.retrofit.ApiService
import retrofit2.Call

class UserRepository(private val apiService: ApiService) {
    fun registerUser(name: String, email: String, password: String): Call<RegisterResponse> {
        return apiService.registerUser(name = name, email = email, password = password)
    }

    fun loginUser(email: String, password: String): Call<LoginResponse> {
        return apiService.loginUser(email = email, password = password)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        @JvmStatic
        fun getInstance(
            apiService: ApiService
        ): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(apiService)
        }.also { instance = it }
    }
}