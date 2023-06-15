package com.capstone.moru.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.moru.data.api.response.ProfileData
import com.capstone.moru.data.api.response.ProfileResponse
import com.capstone.moru.data.api.response.ScheduleListItem
import com.capstone.moru.data.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(private var userRepository: UserRepository): ViewModel() {
    private var _message = MutableLiveData<String>()
    var message: LiveData<String> = _message

    private var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    private var _error = MutableLiveData<Boolean>()
    var error: LiveData<Boolean> = _error

    private var _profile = MutableLiveData<ProfileData>()
    var profile: LiveData<ProfileData> = _profile

    fun getUserToken(): LiveData<String> {
        return userRepository.getUserToken()
    }

    fun logout(){
        viewModelScope.launch {
            userRepository.logout()
        }
    }

    fun getUserEmail(): LiveData<String>{
        return userRepository.getUserEmail()
    }

    fun getUsername(): LiveData<String>{
        return userRepository.getUsername()
    }

    fun getUserProfile(token: String) {
        _isLoading.value = true
        val formattedToken = "Bearer $token"
        val client = userRepository.getUserProfile(formattedToken)
        client.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _error.value = false
                    _profile.value = response.body()?.data!!

                    _message.value = response.message()
                } else {
                    _error.value = true
                    _message.value = "onFailure: ${response.message()} + ${response.code()}"
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                _error.value = true
                _isLoading.value = false
                _message.value = "onFailure: ${t.message.toString()}"
            }

        })
    }
}