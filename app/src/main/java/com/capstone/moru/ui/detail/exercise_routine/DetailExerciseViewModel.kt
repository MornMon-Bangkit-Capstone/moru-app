package com.capstone.moru.ui.detail.exercise_routine

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.moru.data.api.response.DetailRoutineResponse
import com.capstone.moru.data.api.response.ListItem
import com.capstone.moru.data.api.response.ListRoutine
import com.capstone.moru.data.api.response.RoutineResponse
import com.capstone.moru.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailExerciseViewModel(private var userRepository: UserRepository) : ViewModel() {
    private var _exerciseRoutine = MutableLiveData<List<com.capstone.moru.data.api.response.ListItem?>?>()
    val exerciseRoutine: LiveData<List<com.capstone.moru.data.api.response.ListItem?>?> = _exerciseRoutine

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserToken(): LiveData<String> {
        return userRepository.getUserToken()
    }

    fun getExerciseRoutineDetail(token: String, id: String) {
        _isLoading.value = true
        val formatToken = "Bearer $token"
        val client = userRepository.getExerciseRoutineDetail(formatToken, id)

        client.enqueue(object : Callback<com.capstone.moru.data.api.response.RoutineResponse> {
            override fun onResponse(
                call: Call<com.capstone.moru.data.api.response.RoutineResponse>,
                response: Response<com.capstone.moru.data.api.response.RoutineResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _error.value = false
                    _message.value = response.message()

                    _exerciseRoutine.value = response.body()?.list

                    Log.e("DETAIL EXERCISE 1", _message.value.toString())

                } else {
                    _error.value = true
                    _message.value = "onFailure: ${response.message()} + ${response.code()}"

                    Log.e("DETAIL EXERCISE 2", _message.value.toString())

                }
            }

            override fun onFailure(call: Call<com.capstone.moru.data.api.response.RoutineResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = "onFailure: ${t.message.toString()}"

                Log.e("DETAIL EXERCISE 3", _message.value.toString())

            }
        })
    }

}