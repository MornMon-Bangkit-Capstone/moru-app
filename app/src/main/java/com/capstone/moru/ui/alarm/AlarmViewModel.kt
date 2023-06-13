package com.capstone.moru.ui.alarm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.moru.data.api.response.*
import com.capstone.moru.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmViewModel(private val userRepository: UserRepository) : ViewModel() {
    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _exerciseRoutine =
        MutableLiveData<ExerciseListItem?>()
    val exerciseRoutine: LiveData<ExerciseListItem?> =
        _exerciseRoutine

    private var _bookRoutine =
        MutableLiveData<BookListItem?>()
    val bookRoutine: LiveData<BookListItem?> =
        _bookRoutine

    private var _scheduleDetail = MutableLiveData<List<ScheduleDetailListItem?>?>()
    var scheduleDetail: LiveData<List<ScheduleDetailListItem?>?> = _scheduleDetail

    private var _schedule = MutableLiveData<List<ScheduleListItem?>?>()
    var schedule: LiveData<List<ScheduleListItem?>?> = _schedule

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
                    _scheduleDetail.value = response.body()?.list

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

    fun getCurrentSchedule(token: String, date: String){
        _isLoading.value = true
        val formattedToken = "Bearer $token"
        val client = userRepository.getUserScheduleList(formattedToken, date)
        client.enqueue(object: Callback<ScheduleListResponse>{
            override fun onResponse(
                call: Call<ScheduleListResponse>,
                response: Response<ScheduleListResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _error.value = false
                    _schedule.value = response.body()?.list

                    _message.value = response.message()
                }else{
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

    fun getExerciseRoutineDetail(token: String, id: Int, isPublic: Int) {
        _isLoading.value = true
        val formatToken = "Bearer $token"
        val client = userRepository.getExerciseRoutineDetail(formatToken, id, isPublic)

        client.enqueue(object : Callback<ExerciseListResponse> {
            override fun onResponse(
                call: Call<ExerciseListResponse>,
                response: Response<ExerciseListResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _error.value = false
                    _message.value = response.message()

                    _exerciseRoutine.value = response.body()?.list?.firstOrNull()
                } else {
                    _error.value = true
                    _message.value = "onFailure: ${response.message()} + ${response.code()}"
                }
            }

            override fun onFailure(
                call: Call<ExerciseListResponse>,
                t: Throwable
            ) {
                _isLoading.value = false
                _message.value = "onFailure: ${t.message.toString()}"
            }
        })
    }

    fun getBookRoutineDetail(token: String, id: Int, isPublic: Int) {
        _isLoading.value = true
        val formatToken = "Bearer $token"
        val client = userRepository.getBookRoutineDetail(formatToken, id, isPublic)

        client.enqueue(object : Callback<BookListResponse> {
            override fun onResponse(
                call: Call<BookListResponse>,
                response: Response<BookListResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _error.value = false
                    _message.value = response.message()
                    _bookRoutine.value = response.body()?.list?.firstOrNull()
                } else {
                    _error.value = true
                    _message.value = "onFailure: ${response.message()} + ${response.code()}"
                }
            }

            override fun onFailure(
                call: Call<BookListResponse>,
                t: Throwable
            ) {
                _isLoading.value = false
                _message.value = "onFailure: ${t.message.toString()}"
            }
        })
    }

}