package com.capstone.moru.ui.auth.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.moru.data.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private var userRepository: UserRepository) : ViewModel() {
    private val _user = MutableLiveData<com.capstone.moru.data.api.response.LoginResult?>()
    val user: LiveData<com.capstone.moru.data.api.response.LoginResult?> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun saveUserFillProfileStatus(status: Int) {
        viewModelScope.launch {
            userRepository.saveUserFillProfileStatus(status)
        }
    }

    fun userLogin(email: String, password: String) {
        saveUserEmail(email)
        _isLoading.value = true
        val client = userRepository.loginUser(email, password)
        client.enqueue(
            object : Callback<com.capstone.moru.data.api.response.LoginResponse> {
                override fun onResponse(
                    call: Call<com.capstone.moru.data.api.response.LoginResponse>,
                    response: Response<com.capstone.moru.data.api.response.LoginResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        Log.e(TAG, "loginUser: ${response.body()}")

                        _user.value = response.body()?.loginResult
                        saveUserToken(_user.value?.token)
                        saveUserId(_user.value?.userId)
                        _error.value = false
                    } else {
                        Log.e(TAG, "On failure ${response.message()} + ${response.code()}")

                        _message.value = response.message()
                        _error.value = true
                    }
                }

                override fun onFailure(
                    call: Call<com.capstone.moru.data.api.response.LoginResponse>,
                    t: Throwable
                ) {
                    Log.e(TAG, "On failure ${t.message.toString()}")

                    _isLoading.value = false
                    _message.value = t.message.toString()
                    _error.value = true
                }
            }
        )
    }

    fun saveUserToken(token: String?) {
        viewModelScope.launch {
            if (token != null) {
                userRepository.saveUserToken(token)
            }
        }
    }

    fun saveUserId(id: Int?) {
        viewModelScope.launch {
            if (id != null) {
                userRepository.saveUserId(id)
            }
        }
    }

    fun saveUserEmail(email: String) {
        viewModelScope.launch {
            userRepository.saveUserEmail(email)
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}