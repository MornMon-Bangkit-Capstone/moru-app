package com.capstone.moru.data.repository

//import com.capstone.moru.data.db.paging.BooksRoutineRemoteMediator
//import com.capstone.moru.data.db.paging.ExerciseRoutineRemoteMediator
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.capstone.moru.data.api.response.BookListResponse
import com.capstone.moru.data.api.response.DefaultResponse
import com.capstone.moru.data.api.response.ExerciseListResponse
import com.capstone.moru.data.api.response.ProfileResponse
import com.capstone.moru.data.api.retrofit.ApiService
import com.capstone.moru.data.datastore.SettingPreference
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

    fun fillProfile(
        token: String,
        title: String,
        goal: String,
        birthDate: String,
        favBook: String,
        favExercise: String,
        favAuthor: String,
    ): Call<DefaultResponse> {
        return apiService.fillPersonalDataUser(token, title, goal, birthDate, favBook, favExercise, favAuthor)
    }

    fun getUserProfile(token: String): Call<ProfileResponse>{
        return apiService.getUserProfile(token)
    }

    fun getUserToken(): LiveData<String> {
        return preference.getUserToken().asLiveData()
    }

    suspend fun saveUserToken(token: String) {
        return preference.saveUserToken(token)
    }

    fun getUserId(): LiveData<Int> {
        return preference.getUserId().asLiveData()
    }

    suspend fun saveUserId(id: Int) {
        return preference.saveUserId(id)
    }

    fun getUserEmail(): LiveData<String>{
        return preference.getEmailUser().asLiveData()
    }

    suspend fun saveUserEmail(email: String){
        return preference.saveEmailUser(email)
    }

    fun getUserFillProfileStatus(): LiveData<Int>{
        return preference.getFillProfile().asLiveData()
    }

    suspend fun saveUserFillProfileStatus(status: Int){
        return preference.saveFillProfile(status)
    }

    fun getUsername(): LiveData<String>{
        return preference.getUsername().asLiveData()
    }

    suspend fun saveUsername(username: String){
        return preference.saveUsername(username)
    }

    fun getUserAuthor(): LiveData<String>{
        return preference.getUserAuthor().asLiveData()
    }

    suspend fun saveUserAuthor(author: String){
        return preference.saveUserAuthor(author)
    }

    suspend fun logout() {
        return preference.clearCache()
    }

    fun postBookRate(token: String, iSBN: String, bookRating: String): Call<DefaultResponse>{
        return apiService.postBooKRating(token, iSBN, bookRating)
    }

    fun getBookRecommendation(token: String): Call<BookListResponse> {
        return apiService.getBookRecommendation(token)
    }

    fun getExerciseRecommendation(token: String): Call<ExerciseListResponse> {
        return apiService.getExerciseRecommendation(token)
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
        isPublic: String,
        refId: Int,
    ): Call<com.capstone.moru.data.api.response.DefaultResponse> {
        return apiService.postUserSchedule(
            token,
            type,
            title,
            date,
            startTime,
            endTime,
            description,
            isPublic,
            refId,
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
        isPublic: String,
        refId: Int,
    ): Call<com.capstone.moru.data.api.response.DefaultResponse> {
        return apiService.putUserSchedule(
            token,
            id,
            type,
            title,
            date,
            startTime,
            endTime,
            description,
            isPublic,
            refId,
        )
    }



    fun getUserScheduleList(
        token: String,
        date: String
    ): Call<com.capstone.moru.data.api.response.ScheduleListResponse> {
        return apiService.getUserScheduleList(token, date)
    }

    fun getUserScheduleDetail(
        token: String,
        id: Int
    ): Call<com.capstone.moru.data.api.response.ScheduleDetailResponse> {
        return apiService.getUserScheduleDetail(token, id)
    }

    fun deleteUserSchedule(token: String, id: Int): Call<DefaultResponse> {
        return apiService.deleteUserSchedule(token, id)
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