package com.capstone.moru.data.api.retrofit

import com.capstone.moru.data.api.response.*
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface ApiService {
    // *USER*
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
    // *USER*

    // *PAGINATION*
    @GET("routine/exercises")
    fun getAllExercises(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null

    ): Call<RoutineResponse>

    @GET("routine/books")
    fun getAllBooks(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): Call<RoutineResponse>
    // *PAGINATION*

    // *ROUTINE*
    @GET("routine/exercises")
    fun getAllExerciseRoutine(
        @Header("Authorization") token: String,
    ): Call<ExerciseListResponse>

    @GET("routine/books")
    fun findBookRoutine(
        @Header("Authorization") token: String,
        @Query("key") key: String,
    ): Call<BookListResponse>

    @GET("routine/exercises")
    fun findExerciseRoutine(
        @Header("Authorization") token: String,
        @Query("key") key: String,
    ): Call<ExerciseListResponse>

    @GET("routine/books")
    fun getAllBooksRoutine(
        @Header("Authorization") token: String,
    ): Call<BookListResponse>

    @GET("routine/books/{id}/{isPublic}")
    fun getBookRoutineDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Path("isPublic") isPublic: Int,
        ): Call<BookListResponse>

    @GET("routine/exercises/{id}/{isPublic}")
    fun getExerciseRoutineDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Path("isPublic") isPublic: Int,
    ): Call<ExerciseListResponse>
    // *ROUTINE*

    // *SCHEDULE*
    @GET("/schedule")
    fun getUserScheduleList(
        @Header("Authorization") token: String,
    ): Call<ScheduleListResponse>

    @GET("/schedule/{id}")
    fun getUserScheduleDetail(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Call<ScheduleDetailResponse>

    @FormUrlEncoded
    @POST("schedule")
    fun postUserSchedule(
        @Header("Authorization") token: String,
        @Field("type") type: String,
        @Field("name") title: String,
        @Field("date") date: String,
        @Field("startTime") startTime: String,
        @Field("endTime") endTime: String,
        @Field("description") description: String,
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @PUT("schedule/{id}")
    fun putUserSchedule(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Field("type") type: String,
        @Field("title") title: String,
        @Field("date") date: String,
        @Field("startTime") startTime: String,
        @Field("endTime") endTime: String,
        @Field("description") description: String,
    ): Call<DefaultResponse>

    @DELETE("schedule/{id}")
    fun deleteUserSchedule(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Call<DefaultResponse>
    // *SCHEDULE*
}