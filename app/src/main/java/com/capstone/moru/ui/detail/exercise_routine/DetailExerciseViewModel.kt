package com.capstone.moru.ui.detail.exercise_routine

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.moru.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailExerciseViewModel(private var userRepository: UserRepository) : ViewModel() {
    private var _exerciseRoutine =
        MutableLiveData<com.capstone.moru.data.api.response.ExerciseListItem?>()
    val exerciseRoutine: LiveData<com.capstone.moru.data.api.response.ExerciseListItem?> =
        _exerciseRoutine

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserToken(): LiveData<String> {
        return userRepository.getUserToken()
    }

    fun     getExerciseRoutineDetail(token: String, id: Int, isPublic: Int) {
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

}