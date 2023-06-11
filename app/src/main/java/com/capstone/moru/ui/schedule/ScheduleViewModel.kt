package com.capstone.moru.ui.schedule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.moru.data.api.response.ScheduleListItem
import com.capstone.moru.data.api.response.ScheduleListResponse
import com.capstone.moru.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleViewModel(private var userRepository: UserRepository): ViewModel() {
    private var _message = MutableLiveData<String>()
    var message: LiveData<String> = _message

    private var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    private var _error = MutableLiveData<Boolean>()
    var error: LiveData<Boolean> = _error

    private var _schedule = MutableLiveData<List<ScheduleListItem?>?>()
    var schedule: LiveData<List<ScheduleListItem?>?> = _schedule

    fun getUserToken(): LiveData<String> {
        return userRepository.getUserToken()
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

                Log.e("RoutineViewModel", _message.value.toString())
            }

        })
    }
}