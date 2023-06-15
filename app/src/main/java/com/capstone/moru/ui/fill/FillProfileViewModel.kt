package com.capstone.moru.ui.fill

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.moru.data.api.response.DefaultResponse
import com.capstone.moru.data.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FillProfileViewModel(private var userRepository: UserRepository) : ViewModel() {

    private var _message = MutableLiveData<String>()
    var message: LiveData<String> = _message

    private var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    private var _error = MutableLiveData<Boolean>()
    var error: LiveData<Boolean> = _error

    fun getUserToken(): LiveData<String> {
        return userRepository.getUserToken()
    }

    fun getUserId(): LiveData<Int> {
        return userRepository.getUserId()
    }

    fun saveUserFillProfileStatus(status: Int) {
        viewModelScope.launch {
            userRepository.saveUserFillProfileStatus(status)
        }
    }

    fun fillUserProfile(token: String, title: String, goal: String, birthDate: String,favBook: String,favExercise: String, favAuthor: String) {
        val formatterToken = "Bearer $token"
        val client = userRepository.fillProfile(formatterToken, title, goal, birthDate, favBook, favExercise, favAuthor)
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

    fun postBookRating(token: String, iSBN: String, bookRating: String) {
        val formatterToken = "Bearer $token"
        val client = userRepository.postBookRate(token, iSBN, bookRating)
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