package com.capstone.moru.ui.auth.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.moru.data.api.response.LoginResponse
import com.capstone.moru.data.api.response.LoginResult
import com.capstone.moru.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.launch

class LoginViewModel(private var userRepository: UserRepository):ViewModel() {
    private val _user = MutableLiveData<LoginResult?>()
    val user: LiveData<LoginResult?> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun userLogin(email:String, password: String){
        _isLoading.value = true
        val client = userRepository.loginUser(email, password)
        client.enqueue(
            object : Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful){
                        Log.e(TAG, "loginUser: ${response.body()}")

                        _user.value = response.body()?.loginResult
                        saveUserToken(_user.value?.token)
                        _error.value = false
                    }else{
                        Log.e(TAG, "On failure ${response.message()} + ${response.code()}")

                        _message.value = response.message()
                        _error.value = true
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e(TAG, "On failure ${t.message.toString()}")

                    _isLoading.value = false
                    _message.value = t.message.toString()
                    _error.value = true
                }
            }
        )
    }

    fun saveUserToken(token: String?){
        viewModelScope.launch {
           if (token != null){
               userRepository.saveUserToken(token)
           }
        }
    }

    companion object{
        private const val TAG = "LoginViewModel"

    }

}