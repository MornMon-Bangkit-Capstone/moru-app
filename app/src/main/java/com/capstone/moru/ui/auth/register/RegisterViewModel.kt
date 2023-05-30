package com.capstone.moru.ui.auth.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.moru.data.api.response.LoginResult
import com.capstone.moru.data.api.response.RegisterResponse
import com.capstone.moru.data.repository.UserRepository
import com.capstone.moru.ui.auth.login.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private var userRepository: UserRepository) : ViewModel() {
    private val _user = MutableLiveData<String?>()
    val user: LiveData<String?> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun userRegister(email: String, password: String, passwordConfirm: String) {
        _isLoading.value = true
        val client = userRepository.registerUser(email, password, passwordConfirm)
        client.enqueue(
            object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    _isLoading.value = false

                    if (response.isSuccessful) {
                        Log.e(TAG, "registerUser: ${response.body()}")

                        _user.value = response.body()?.message
                        _error.value = false
                    } else {
                        Log.e(TAG, "On failure ${response.message()} + ${response.code()}")

                        _message.value = response.message()
                        _error.value = true
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Log.e(TAG, "On failure ${t.message.toString()}")

                    _isLoading.value = false
                    _message.value = t.message.toString()
                    _error.value = true
                }

            }
        )
    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }

}