package com.capstone.moru.data.api.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(client: OkHttpClient): ApiService {
            val retrofit = Retrofit.Builder().baseUrl("https://moru-api-backend-inq7f5uz7q-et.a.run.app/")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()
            return retrofit.create(ApiService::class.java)
        }
    }
}