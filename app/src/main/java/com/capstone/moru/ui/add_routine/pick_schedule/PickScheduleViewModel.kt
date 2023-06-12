package com.capstone.moru.ui.add_routine.pick_schedule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.moru.data.api.response.DefaultResponse
import com.capstone.moru.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickScheduleViewModel(private var userRepository: UserRepository) : ViewModel() {

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun getUserToken(): LiveData<String> {
        return userRepository.getUserToken()
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
    ) {
        _isLoading.value = true
        val formatToken = "Bearer $token"
        val client = userRepository.postUserSchedule(formatToken, type, title, date, startTime, endTime, description, isPublic, refId)

        client.enqueue(object: Callback<com.capstone.moru.data.api.response.DefaultResponse>{
            override fun onResponse(
                call: Call<com.capstone.moru.data.api.response.DefaultResponse>,
                response: Response<com.capstone.moru.data.api.response.DefaultResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _error.value = false
                    _message.value = response.message()
                    Log.e("PICK", _message.value.toString())
                }else{
                    _error.value = true
                    _message.value = "onFailure: ${response.message()} + ${response.code()}"
                }
            }

            override fun onFailure(call: Call<com.capstone.moru.data.api.response.DefaultResponse>, t: Throwable) {
                _error.value = true
                _isLoading.value = false
                _message.value = "onFailure: ${t.message.toString()}"
            }
        })
    }
}