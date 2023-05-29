package com.capstone.moru.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.moru.data.repository.UserRepository

class SplashViewModel(private var userRepository: UserRepository) : ViewModel() {
    fun getUserToken(): LiveData<String> {
       return userRepository.getUserToken()
    }
}