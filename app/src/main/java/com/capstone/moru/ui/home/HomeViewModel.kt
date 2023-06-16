package com.capstone.moru.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.moru.data.api.response.ProfileData
import com.capstone.moru.data.api.response.ProfileResponse
import com.capstone.moru.data.api.response.ScheduleListItem
import com.capstone.moru.data.api.response.ScheduleListResponse
import com.capstone.moru.data.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private var userRepository: UserRepository) : ViewModel() {

    private var _message = MutableLiveData<String>()
    var message: LiveData<String> = _message

    private var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    private var _error = MutableLiveData<Boolean>()
    var error: LiveData<Boolean> = _error

    private var _message1 = MutableLiveData<String>()
    var message1: LiveData<String> = _message1

    private var _isLoading1 = MutableLiveData<Boolean>()
    var isLoading1: LiveData<Boolean> = _isLoading1

    private var _error1 = MutableLiveData<Boolean>()
    var error1: LiveData<Boolean> = _error1

    private var _schedule = MutableLiveData<List<ScheduleListItem?>?>()
    var schedule: LiveData<List<ScheduleListItem?>?> = _schedule

    private var _profile = MutableLiveData<ProfileData>()
    var profile: LiveData<ProfileData> = _profile

    private var _bookRoutine =
        MutableLiveData<List<com.capstone.moru.data.api.response.BookListItem?>?>()
    var bookRoutine: LiveData<List<com.capstone.moru.data.api.response.BookListItem?>?> =
        _bookRoutine

    private var _exerciseRoutine =
        MutableLiveData<List<com.capstone.moru.data.api.response.ExerciseListItem?>?>()
    var exerciseRoutine: LiveData<List<com.capstone.moru.data.api.response.ExerciseListItem?>?> =
        _exerciseRoutine

    fun getUserToken(): LiveData<String> {
        return userRepository.getUserToken()
    }

    fun getFillProfileStatus(): LiveData<Int> {
        return userRepository.getUserFillProfileStatus()
    }

    fun getExerciseRecommendation(token: String) {
        _isLoading1.value = true
        val formatToken = "Bearer $token"
        val client = userRepository.getExerciseRecommendation(formatToken)

        client.enqueue(object : Callback<com.capstone.moru.data.api.response.ExerciseListResponse> {
            override fun onResponse(
                call: Call<com.capstone.moru.data.api.response.ExerciseListResponse>,
                response: Response<com.capstone.moru.data.api.response.ExerciseListResponse>
            ) {
                _isLoading1.value = false
                if (response.isSuccessful) {
                    _error1.value = false
                    _message1.value = response.message()

                    _exerciseRoutine.value = response.body()?.list
                } else {
                    _error1.value = true
                    _message1.value = "onFailure: ${response.message()} + ${response.code()}"
                }
            }

            override fun onFailure(
                call: Call<com.capstone.moru.data.api.response.ExerciseListResponse>,
                t: Throwable
            ) {
                _error1.value = true
                _isLoading1.value = false
                _message1.value = "onFailure: ${t.message.toString()}"

                Log.e("RoutineViewModel", _message.value.toString())
            }
        })
    }

    fun getBookRecommendation(token: String) {
        _isLoading1.value = true
        val formatToken = "Bearer $token"
        val client = userRepository.getBookRecommendation(formatToken)

        client.enqueue(object : Callback<com.capstone.moru.data.api.response.BookListResponse> {
            override fun onResponse(
                call: Call<com.capstone.moru.data.api.response.BookListResponse>,
                response: Response<com.capstone.moru.data.api.response.BookListResponse>
            ) {

                _isLoading1.value = false
                if (response.isSuccessful) {
                    _error1.value = false
                    _message1.value = response.message()

                    _bookRoutine.value = response.body()?.list
                } else {
                    _error1.value = true
                    _message1.value = "onFailure: ${response.message()} + ${response.code()}"

                    Log.e("RoutineViewModel", _message.value.toString())

                }
            }

            override fun onFailure(
                call: Call<com.capstone.moru.data.api.response.BookListResponse>,
                t: Throwable
            ) {
                _error1.value = true
                _isLoading1.value = false
                _message1.value = "onFailure: ${t.message.toString()}"

                Log.e("RoutineViewModel", _message.value.toString())
            }
        })
    }

    fun getUserProfile(token: String) {
        _isLoading.value = true
        val formattedToken = "Bearer $token"
        val client = userRepository.getUserProfile(formattedToken)
        client.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _error.value = false
                    _profile.value = response.body()?.data!!

                    saveUsername(response.body()?.data!!.username.toString())
                    saveUserImage(response.body()?.data!!.profilePicture.toString())

                    Log.e("IMAGE2", response.body()?.data!!.profilePicture.toString())

                    _message.value = response.message()
                } else {
                    _error.value = true
                    _message.value = "onFailure: ${response.message()} + ${response.code()}"
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                _error.value = true
                _isLoading.value = false
                _message.value = "onFailure: ${t.message.toString()}"
            }

        })
    }

    fun getCurrentSchedule(token: String, date: String) {
        _isLoading.value = true
        val formattedToken = "Bearer $token"
        val client = userRepository.getUserScheduleList(formattedToken, date)
        client.enqueue(object : Callback<ScheduleListResponse> {
            override fun onResponse(
                call: Call<ScheduleListResponse>,
                response: Response<ScheduleListResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _error.value = false
                    _schedule.value = response.body()?.list

                    _message.value = response.message()
                } else {
                    _error.value = true
                    _message.value = "onFailure: ${response.message()} + ${response.code()}"
                }
            }

            override fun onFailure(call: Call<ScheduleListResponse>, t: Throwable) {
                _error.value = true
                _isLoading.value = false
                _message.value = "onFailure: ${t.message.toString()}"
            }

        })
    }

    fun saveUsername(username: String) {
        viewModelScope.launch {
            userRepository.saveUsername(username)
        }
    }

    fun saveUserImage(imageUrl: String) {
        viewModelScope.launch {
            userRepository.saveImageUser(imageUrl)
        }
    }

    fun getImageUser(): LiveData<String> {
        return userRepository.getImageUser()
    }

    fun getUsername(): LiveData<String> {
        return userRepository.getUsername()
    }
}