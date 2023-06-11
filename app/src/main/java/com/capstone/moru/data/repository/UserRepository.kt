package com.capstone.moru.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.*
import com.capstone.moru.data.api.response.*
import com.capstone.moru.data.api.retrofit.ApiService
import com.capstone.moru.data.datastore.SettingPreference
import com.capstone.moru.data.db.model.BooksRoutineModel
import com.capstone.moru.data.db.model.ExerciseRoutineModel
//import com.capstone.moru.data.db.paging.BooksRoutineRemoteMediator
//import com.capstone.moru.data.db.paging.ExerciseRoutineRemoteMediator
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
    ): Call<com.capstone.moru.data.api.response.RegisterResponse> {
        return apiService.registerUser(email, password, passwordConfirm)
    }

    fun loginUser(
        email: String,
        password: String
    ): Call<com.capstone.moru.data.api.response.LoginResponse> {
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

    suspend fun setUserIsFirstTime(status: Boolean) {
        return preference.setUserIsFirstTime(status)
    }

    suspend fun getUserIsFirstTime(): LiveData<Boolean> {
        return preference.getUserIsFirstTime().asLiveData()
    }

    fun getAllExerciseRoutine(token: String): Call<com.capstone.moru.data.api.response.ExerciseListResponse> {
        return apiService.getAllExerciseRoutine(token)
    }

    fun getAllBooksRoutine(token: String): Call<com.capstone.moru.data.api.response.BookListResponse> {
        return apiService.getAllBooksRoutine(token)
    }

    fun getBookRoutineDetail(
        token: String,
        id: Int,
        isPublic: Int
    ): Call<BookListResponse> {
        return apiService.getBookRoutineDetail(token, id, isPublic)
    }

    fun getExerciseRoutineDetail(
        token: String,
        id: Int,
        isPublic: Int
    ): Call<ExerciseListResponse> {
        return apiService.getExerciseRoutineDetail(token, id, isPublic)
    }

    fun findBookRoutine(token: String, key: String): Call<BookListResponse> {
        return apiService.findBookRoutine(token, key)
    }

    fun findExerciseRoutine(token: String, key: String): Call<ExerciseListResponse> {
        return apiService.findExerciseRoutine(token, key)
    }

    fun postUserSchedule(
        token: String,
        type: String,
        title: String,
        date: String,
        startTime: String,
        endTime: String,
        description: String,
    ): Call<com.capstone.moru.data.api.response.DefaultResponse> {
        return apiService.postUserSchedule(
            token,
            type,
            title,
            date,
            startTime,
            endTime,
            description
        )
    }

    fun updateUserSchedule(
        token: String,
        id: String,
        type: String,
        title: String,
        date: String,
        startTime: String,
        endTime: String,
        description: String,
    ): Call<com.capstone.moru.data.api.response.DefaultResponse> {
        return apiService.putUserSchedule(
            token,
            id,
            type,
            title,
            date,
            startTime,
            endTime,
            description
        )
    }

    fun getUserScheduleList(token: String, date: String): Call<com.capstone.moru.data.api.response.ScheduleListResponse> {
        return apiService.getUserScheduleList(token, date)
    }

    fun getUserScheduleDetail(
        token: String,
        id: String
    ): Call<com.capstone.moru.data.api.response.ScheduleDetailResponse> {
        return apiService.getUserScheduleDetail(token, id)
    }


    // *FOR PAGINATION*
//    @OptIn(ExperimentalPagingApi::class)
//    fun getAllExercise(token: String): LiveData<PagingData<ExerciseRoutineModel>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 5,
//                enablePlaceholders = false,
//            ),
//            remoteMediator = ExerciseRoutineRemoteMediator(
//                userRoutineDatabase,
//                apiService,
//                token,
//            ),
//            pagingSourceFactory = {
//                userRoutineDatabase.userRoutineDao().getAllUserExerciseRoutines()
//            }
//        ).liveData
//    }

//    @OptIn(ExperimentalPagingApi::class)
//    fun getAllBooks(token: String): LiveData<PagingData<BooksRoutineModel>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 5,
//                enablePlaceholders = false,
//            ),
//            remoteMediator = BooksRoutineRemoteMediator(
//                userRoutineDatabase,
//                apiService,
//                token,
//            ),
//            pagingSourceFactory = {
//                userRoutineDatabase.userRoutineDao().getAllUserBooksRoutines()
//            }
//        ).liveData
//    }
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