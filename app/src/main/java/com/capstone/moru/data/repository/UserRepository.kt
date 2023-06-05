package com.capstone.moru.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.*
import com.capstone.moru.data.api.response.ListItem
import com.capstone.moru.data.api.response.LoginResponse
import com.capstone.moru.data.api.response.RegisterResponse
import com.capstone.moru.data.api.response.RoutineResponse
import com.capstone.moru.data.api.retrofit.ApiService
import com.capstone.moru.data.datastore.SettingPreference
import com.capstone.moru.data.db.model.BooksRoutineModel
import com.capstone.moru.data.db.model.ExerciseRoutineModel
import com.capstone.moru.data.db.paging.BooksRoutineRemoteMediator
import com.capstone.moru.data.db.paging.ExerciseRoutineRemoteMediator
import com.capstone.moru.data.db.user_routine.UserRoutineDatabase
import retrofit2.Call

class UserRepository(
    private val apiService: ApiService,
    private val preference: SettingPreference,
    private val userRoutineDatabase: UserRoutineDatabase,
) {
    fun registerUser(
        email: String,
        password: String,
        passwordConfirm: String
    ): Call<RegisterResponse> {
        return apiService.registerUser(email, password, passwordConfirm)
    }

    fun loginUser(email: String, password: String): Call<LoginResponse> {
        return apiService.loginUser(email, password)
    }

    fun getUserToken(): LiveData<String> {
        return preference.getUserToken().asLiveData()
    }

    suspend fun saveUserToken(token: String) {
        return preference.saveUserToken(token)
    }

    suspend fun logout() {
        return preference.clearCache()
    }

    fun getAllExerciseRoutine():Call<RoutineResponse>{
        return apiService.getAllExerciseRoutine()
    }

    fun getAllBooksRoutine():Call<RoutineResponse>{
        return apiService.getAllBooksRoutine()
    }

    // *FOR PAGINATION*
    @OptIn(ExperimentalPagingApi::class)
    fun getAllExercise(token: String): LiveData<PagingData<ExerciseRoutineModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false,
            ),
            remoteMediator = ExerciseRoutineRemoteMediator(
                userRoutineDatabase,
                apiService,
                token,
            ),
            pagingSourceFactory = {
               userRoutineDatabase.userRoutineDao().getAllUserExerciseRoutines()
            }
        ).liveData
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getAllBooks(token: String): LiveData<PagingData<BooksRoutineModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false,
            ),
            remoteMediator = BooksRoutineRemoteMediator(
                userRoutineDatabase,
                apiService,
                token,
            ),
            pagingSourceFactory = {
                userRoutineDatabase.userRoutineDao().getAllUserBooksRoutines()
            }
        ).liveData
    }
    // *FOR PAGINATION*



    companion object {
        @Volatile
        private var instance: UserRepository? = null

        @JvmStatic
        fun getInstance(
            apiService: ApiService,
            preference: SettingPreference,
            userRoutineDatabase: UserRoutineDatabase,
        ): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(apiService, preference, userRoutineDatabase)
        }.also { instance = it }
    }
}