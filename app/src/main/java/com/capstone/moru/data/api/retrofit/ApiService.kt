package com.capstone.moru.data.api.retrofit

import com.capstone.moru.data.api.response.*
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface ApiService {

    @FormUrlEncoded
    @POST("auth/register")
    fun registerUser(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("passwordConfirm") passwordConfirm: String,
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("/auth/fill/data")
    fun fillPersonalDataUser(
        @Field("name") name: String,
        @Field("birthdate") birthDate: String,
        @Field("goal") goal: String,
    ): Call<FillProfileResponse>

    @FormUrlEncoded
    @GET("profile/{id}")
    fun getProfileUser(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<ProfileResponse>

    @PUT("profile/{id}")
    fun updateProfileUser(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Field("Name") name: String,
        @Field("Date") date: Date,
        @Field("Goals") goals: String,
        @Field("Favorite Book Category") favBookCategory: String,
        @Field("Favorite Book") favBook: String,
        @Field("Favorite Author") favAuthor: String,
        @Field("Favorite Exercise Category") favExerciseCategory: String,
        @Field("Favorite Exercise") favExercise: String,
        @Field("Favorite Duration") faveExerciseDuration: String,
    ): Call<ProfileResponse>

    @GET("routine/exercises")
    fun getAllExercises(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null

    ): Call<RoutineResponse>
}