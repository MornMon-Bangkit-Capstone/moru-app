package com.capstone.moru.ui.detail.user_routine

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.moru.data.api.response.DefaultResponse
import com.capstone.moru.data.api.response.ScheduleDetailListItem
import com.capstone.moru.data.api.response.ScheduleDetailResponse
import com.capstone.moru.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRoutineViewModel(private var userRepository: UserRepository) : ViewModel() {

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _exerciseRoutine =
        MutableLiveData<com.capstone.moru.data.api.response.ExerciseListItem?>()
    val exerciseRoutine: LiveData<com.capstone.moru.data.api.response.ExerciseListItem?> =
        _exerciseRoutine

    private var _bookRoutine =
        MutableLiveData<com.capstone.moru.data.api.response.BookListItem?>()
    val bookRoutine: LiveData<com.capstone.moru.data.api.response.BookListItem?> =
        _bookRoutine

    private var _schedule = MutableLiveData<List<ScheduleDetailListItem?>?>()
    var schedule: LiveData<List<ScheduleDetailListItem?>?> = _schedule

    fun getUserToken(): LiveData<String> {
        return userRepository.getUserToken()
    }

    fun getUserScheduleDetail(token: String, id: Int) {
        _isLoading.value = true
        val formattedToken = "Bearer $token"
        val client = userRepository.getUserScheduleDetail(formattedToken, id)
        client.enqueue(object : Callback<ScheduleDetailResponse> {
            override fun onResponse(
                call: Call<ScheduleDetailResponse>,
                response: Response<ScheduleDetailResponse>
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

            override fun onFailure(call: Call<ScheduleDetailResponse>, t: Throwable) {
                _error.value = true
                _isLoading.value = false
                _message.value = "onFailure: ${t.message.toString()}"
            }
        })
    }

    fun getExerciseRoutineDetail(token: String, id: Int, isPublic: Int) {
        _isLoading.value = true
        val formatToken = "Bearer $token"
        val client = userRepository.getExerciseRoutineDetail(formatToken, id, isPublic)

        client.enqueue(object : Callback<com.capstone.moru.data.api.response.ExerciseListResponse> {
            override fun onResponse(
                call: Call<com.capstone.moru.data.api.response.ExerciseListResponse>,
                response: Response<com.capstone.moru.data.api.response.ExerciseListResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _error.value = false
                    _message.value = response.message()

                    _exerciseRoutine.value = response.body()?.list?.firstOrNull()

                    Log.e("DETAIL EXERCISE 1", _message.value.toString())

                } else {
                    _error.value = true
                    _message.value = "onFailure: ${response.message()} + ${response.code()}"

                    Log.e("DETAIL EXERCISE 2", _message.value.toString())

                }
            }

            override fun onFailure(
                call: Call<com.capstone.moru.data.api.response.ExerciseListResponse>,
                t: Throwable
            ) {
                _isLoading.value = false
                _message.value = "onFailure: ${t.message.toString()}"

                Log.e("DETAIL EXERCISE 3", _message.value.toString())

            }
        })
    }

    fun getBookRoutineDetail(token: String, id: Int, isPublic: Int) {
        _isLoading.value = true
        val formatToken = "Bearer $token"
        val client = userRepository.getBookRoutineDetail(formatToken, id, isPublic)

        client.enqueue(object : Callback<com.capstone.moru.data.api.response.BookListResponse> {
            override fun onResponse(
                call: Call<com.capstone.moru.data.api.response.BookListResponse>,
                response: Response<com.capstone.moru.data.api.response.BookListResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _error.value = false
                    _message.value = response.message()

                    Log.e("DETAIL BOOK 1", response.toString())
                    _bookRoutine.value = response.body()?.list?.firstOrNull()
                } else {
                    _error.value = true
                    _message.value = "onFailure: ${response.message()} + ${response.code()}"
                    Log.e("DETAIL BOOK 2", response.toString())
                }
            }

            override fun onFailure(
                call: Call<com.capstone.moru.data.api.response.BookListResponse>,
                t: Throwable
            ) {
                _isLoading.value = false
                _message.value = "onFailure: ${t.message.toString()}"
                Log.e("DETAIL BOOK 3", t.message.toString())

            }
        })
    }

    fun updateSchedule(
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
    ) {
        _isLoading.value = true
        val formatToken = "Bearer $token"
        val client = userRepository.updateUserSchedule(
            formatToken,
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

        client.enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _error.value = false
                    _message.value = response.message()
                } else {
                    _error.value = true
                    _message.value = "onFailure: ${response.message()} + ${response.code()}"
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                _error.value = true
                _isLoading.value = false
                _message.value = "onFailure: ${t.message.toString()}"
            }

        })
    }

    fun deleteSchedule(
        token: String,
        id: Int,
    ) {
        _isLoading.value = true
        val formatToken = "Bearer $token"
        val client = userRepository.deleteUserSchedule(formatToken, id)
        client.enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _error.value = false
                    _message.value = response.message()
                } else {
                    _error.value = true
                    _message.value = "onFailure: ${response.message()} + ${response.code()}"
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                _error.value = true
                _isLoading.value = false
                _message.value = "onFailure: ${t.message.toString()}"
            }

        })

    }

}