package com.capstone.moru.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.capstone.moru.data.api.response.LoginResponse
import com.capstone.moru.data.api.response.RegisterResponse
import com.capstone.moru.data.api.retrofit.ApiService
import com.capstone.moru.data.datastore.SettingPreference
import retrofit2.Call

class UserRepository(
    private val apiService: ApiService,
    private val preference: SettingPreference,
) {
    fun registerUser( email: String, password: String, passwordConfirm: String): Call<RegisterResponse> {
        return apiService.registerUser(email, password, passwordConfirm)
    }

    fun loginUser(email: String, password: String): Call<LoginResponse> {
        return apiService.loginUser(email, password)
    }

    fun getUserToken(): LiveData<String> {
        return preference.getUserToken().asLiveData()
    }

    suspend fun saveUserToken(token:String){
        return preference.saveUserToken(token)
    }

    suspend fun logout(){
        return preference.clearCache()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        @JvmStatic
        fun getInstance(
            apiService: ApiService,
            preference: SettingPreference,
        ): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(apiService, preference)
        }.also { instance = it }
    }
}